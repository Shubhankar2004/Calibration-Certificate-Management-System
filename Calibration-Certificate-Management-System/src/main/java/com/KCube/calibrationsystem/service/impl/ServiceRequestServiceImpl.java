package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.ServiceRequestDTO;

import com.KCube.calibrationsystem.enums.Status;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.ServiceRequestMapper;
import com.KCube.calibrationsystem.model.Company;
import com.KCube.calibrationsystem.model.Engineer;
import com.KCube.calibrationsystem.model.ServiceRequest;
import com.KCube.calibrationsystem.repository.CompanyRepository;
import com.KCube.calibrationsystem.repository.EngineerRepository;
import com.KCube.calibrationsystem.repository.ServiceRequestRepository;
import com.KCube.calibrationsystem.service.EmailService;
import com.KCube.calibrationsystem.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {

    @Autowired
    private ServiceRequestRepository repository;
    
    @Autowired
    private EngineerRepository engineerRepo;
    
    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private ServiceRequestMapper mapper;
    
    @Autowired
    private EmailService email;

//    @Override
//    public ServiceRequestDTO create(ServiceRequestDTO dto) {
//        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
//    }
    
    @Override
    public ServiceRequestDTO create(ServiceRequestDTO dto) {
        ServiceRequest entity = mapper.toEntity(dto);

        // Link company and engineer by IDs
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        entity.setCompany(company);

        if (dto.getEngineerId() != null) {
            Engineer engineer = engineerRepo.findById(dto.getEngineerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));
            entity.setEngineer(engineer);
        }
        
        if(dto.getStatus().equals(null)) {
        	entity.setStatus(Status.PENDING);
        }


        ServiceRequest saved = repository.save(entity);

        // Send email to client
        if (saved.getEmail() != null && !saved.getEmail().isEmpty()) {
            String subject = "Service Request Created – SunTech Calibration Laboratory";
            String body = "Dear " + saved.getContactPerson() + ",\n\n" +
                    "Your service request (" + saved.getServiceRequestNumber() + ") has been successfully created.\n" +
                    "Status: " + saved.getStatus() + "\n" +
                    "Calibration Type: " + saved.getCalibrationType() + "\n" +
                    "Scheduled Date: " + saved.getAgreedDateForCalibration() + "\n\n" +
                    "We will keep you informed about the next steps.\n\n" +
                    "Best regards,\nSunTech Calibration Laboratory";
            email.sendEmail(saved.getEmail(), subject, body);
        }

        return mapper.toDTO(saved);
    }

    @Override
    public ServiceRequestDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with ID: " + id)));
    }

    @Override
    public List<ServiceRequestDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

//    @Override
//    public ServiceRequestDTO update(Long id, ServiceRequestDTO dto) {
//        ServiceRequest entity = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with ID: " + id));
//
//        entity.setServiceRequestNumber(dto.getServiceRequestNumber());
//        entity.setRequestDate(dto.getRequestDate());
//        entity.setCalibrationAsPerStandard(dto.getCalibrationAsPerStandard());
//        entity.setValidityPeriod(dto.getValidityPeriod());
//        entity.setFrequencyRequired(dto.getFrequencyRequired());
//        return mapper.toDTO(repository.save(entity));
//    }
    
    @Override
    public ServiceRequestDTO update(Long id, ServiceRequestDTO dto) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with ID: " + id));

        boolean statusChanged = false;
        String oldStatus = entity.getStatus().toString();

        entity.setServiceRequestNumber(dto.getServiceRequestNumber());
        entity.setRequestDate(dto.getRequestDate());
        entity.setCalibrationAsPerStandard(dto.getCalibrationAsPerStandard());
        entity.setValidityPeriod(dto.getValidityPeriod());
        entity.setFrequencyRequired(dto.getFrequencyRequired());
        entity.setCertificatesSentBy(dto.getCertificatesSentBy());
        entity.setConformityStatementRequired(dto.getConformityStatementRequired());
        entity.setCalibrationScopeAccepted(dto.getCalibrationScopeAccepted());
        entity.setCommercialTermsAccepted(dto.getCommercialTermsAccepted());
        entity.setRepairingRequired(dto.getRepairingRequired());
        entity.setAgreedDateForCalibration(dto.getAgreedDateForCalibration());
        entity.setCalibrationType(dto.getCalibrationType());
        entity.setContactNumber(dto.getContactNumber());
        entity.setContactPerson(dto.getContactPerson());
        entity.setEmail(dto.getEmail());

        // Handle status update
        if (dto.getStatus() != null) {
            Status newStatus = Status.valueOf(dto.getStatus().toUpperCase());
            if (!entity.getStatus().equals(newStatus)) {
                entity.setStatus(newStatus);
                statusChanged = true;
            }
        }

        ServiceRequest updated = repository.save(entity);

        // Send update email only if status changed
        if (statusChanged && updated.getEmail() != null && !updated.getEmail().isEmpty()) {
            String subject = "Service Request Status Updated – SunTech Calibration Laboratory";
            String body = "Dear " + updated.getContactPerson() + ",\n\n" +
                    "The status of your service request (" + updated.getServiceRequestNumber() + ") has been updated.\n" +
                    "Old Status: " + oldStatus + "\n" +
                    "New Status: " + updated.getStatus() + "\n\n" +
                    "Please reach out if you have any questions.\n\n" +
                    "Regards,\nSunTech Calibration Laboratory";
            email.sendEmail(updated.getEmail(), subject, body);
        }

        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ServiceRequestDTO> searchByCalibrationType(String type) {
        return repository.findByCalibrationType(type)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ServiceRequestDTO> searchByRequestNumber(String number) {
        return repository.findByServiceRequestNumberContainingIgnoreCase(number)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ServiceRequestDTO> filterByDateRange(LocalDate start, LocalDate end) {
        return repository.findByRequestDateBetween(start, end)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}


package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.CalibrationCertificateDTO;
import com.KCube.calibrationsystem.enums.CalibrationType;
import com.KCube.calibrationsystem.enums.CertificateStatus;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.CalibrationCertificateMapper;
import com.KCube.calibrationsystem.model.CalibrationCertificate;
import com.KCube.calibrationsystem.model.ClientMachines;
import com.KCube.calibrationsystem.model.Company;
import com.KCube.calibrationsystem.model.RawData;
import com.KCube.calibrationsystem.model.Scales;
import com.KCube.calibrationsystem.model.ServiceRequest;
import com.KCube.calibrationsystem.model.TestBlocks;
import com.KCube.calibrationsystem.model.helper.ObservedReading;
import com.KCube.calibrationsystem.repository.CalibrationCertificateRepository;
import com.KCube.calibrationsystem.repository.RawDataRepository;
import com.KCube.calibrationsystem.repository.TestBlockRepository;
import com.KCube.calibrationsystem.service.CalibrationCertificateService;
import com.KCube.calibrationsystem.service.EmailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CalibrationCertificateServiceImpl implements CalibrationCertificateService {

    private final CalibrationCertificateRepository repository;
    private final RawDataRepository rawDataRepo;
    private final TestBlockRepository testBlockRepo;
    private final ExcelTemplatePopulator excelPopulator;
    private final CalibrationCertificateMapper mapper;
    private EmailService email;

    
//    public CalibrationCertificate generateCertificate(Long certId) throws IOException {
//        CalibrationCertificate cert = repository.findById(certId)
//                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + certId));
//
//        ClientMachines cm = cert.getClientMachine();
//        RawData rawData = rawDataRepo.findByClientMachineId(cm.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Raw data not found for ClientMachine ID: " + cm.getId()));
//
//        if (rawData.getRawData() == null) {
//            throw new IllegalStateException("RawData JSON is missing for ClientMachine ID: " + cm.getId());
//        }
//
//        ServiceRequest sr = cm.getServiceRequest();
//        Company company = sr.getCompany();
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rawJson = mapper.readTree(rawData.getRawData());
//        List<ObservedReading> readings = mapper.convertValue(
//                rawJson.get("observedReadings"),
//                new TypeReference<List<ObservedReading>>() {}
//        );
//
//        List<Scales> selectedScales = cm.getSelectedScales();
//        Map<Long, List<TestBlocks>> scaleTestBlockMap = new HashMap<>();
//        for (Scales scale : selectedScales) {
//            List<TestBlocks> blocks = testBlockRepo.findTop3ByScaleIdOrderByIdAsc(scale.getId());
//            scaleTestBlockMap.put(scale.getId(), blocks);
//        }
//
//        LocalDate nextDate = rawData.getServiceDate().plusMonths(sr.getFrequencyRequired());
//        CalibrationType calibrationType = sr.getCalibrationType();
//
//        String filePath = excelPopulator.populateExcelTemplate(cert, cm, sr, company, rawData,
//                selectedScales, scaleTestBlockMap, readings, nextDate, calibrationType);
//
//        cert.setExcelFilePath(filePath);
//        return repository.save(cert);
//    }
    
    public CalibrationCertificate generateCertificate(Long certId) throws IOException {
        CalibrationCertificate cert = repository.findById(certId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + certId));

        ClientMachines cm = cert.getClientMachine();
        RawData rawData = rawDataRepo.findByClientMachineId(cm.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw data not found for ClientMachine ID: " + cm.getId()));

        if (rawData.getRawData() == null) {
            throw new IllegalStateException("RawData JSON is missing for ClientMachine ID: " + cm.getId());
        }

        ServiceRequest sr = cm.getServiceRequest();
        Company company = sr.getCompany();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rawJson = mapper.readTree(rawData.getRawData());
        List<ObservedReading> readings = mapper.convertValue(
                rawJson.get("observedReadings"),
                new TypeReference<List<ObservedReading>>() {}
        );

        List<Scales> selectedScales = cm.getSelectedScales();
        Map<Long, List<TestBlocks>> scaleTestBlockMap = new HashMap<>();
        for (Scales scale : selectedScales) {
            List<TestBlocks> blocks = testBlockRepo.findTop3ByScaleIdOrderByIdAsc(scale.getId());
            scaleTestBlockMap.put(scale.getId(), blocks);
        }

        LocalDate nextDate = rawData.getServiceDate().plusMonths(sr.getFrequencyRequired());
        CalibrationType calibrationType = sr.getCalibrationType();

        // Generate certificate
        String filePath = excelPopulator.populateExcelTemplate(cert, cm, sr, company, rawData,
                selectedScales, scaleTestBlockMap, readings, nextDate, calibrationType);

        cert.setExcelFilePath(filePath);
        cert.setIssueDate(LocalDate.now());
        cert.setExpiryDate(nextDate);
        cert.setCertificateStatus(CertificateStatus.GENERATED);
        CalibrationCertificate savedCert = repository.save(cert);

        // Send email notification to company
        if (company.getEmail() != null && !company.getEmail().isEmpty()) {
            String subject = "Calibration Certificate Generated – SunTech Calibration Laboratory";
            String downloadLink = "http://your-domain.com/api/certificates/download/" + savedCert.getId(); // replace with actual endpoint
            String body = "Dear " + company.getContactPerson() + ",\n\n" +
                    "We are pleased to inform you that the calibration certificate for your service request "
                    + sr.getServiceRequestNumber() + " has been generated.\n" +
                    "Certificate Number: " + savedCert.getCertificateNumber() + "\n" +
                    "Issue Date: " + savedCert.getIssueDate() + "\n\n" +
                    "You can download the certificate using the following link:\n" +
                    downloadLink + "\n\n" +
                    "Best regards,\nSunTech Calibration Laboratory";
            email.sendEmail(company.getEmail(), subject, body);
        }

        return savedCert;
    }


    @Override
    public CalibrationCertificateDTO create(CalibrationCertificateDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public CalibrationCertificateDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + id)));
    }

    @Override
    public List<CalibrationCertificateDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

//    @Override
//    public CalibrationCertificateDTO update(Long id, CalibrationCertificateDTO dto) {
//        CalibrationCertificate entity = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + id));
//        entity.setCertificateNumber(dto.getCertificateNumber());
//        entity.setIssueDate(dto.getIssueDate());
//        return mapper.toDTO(repository.save(entity));
//    }
    
    @Override
    public CalibrationCertificateDTO update(Long id, CalibrationCertificateDTO dto) {
        CalibrationCertificate entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + id));

        String oldStatus = entity.getCertificateStatus() != null ? entity.getCertificateStatus().name() : "NONE";
        String newStatus = dto.getCertificateStatus();

        // Update fields
        entity.setCertificateNumber(dto.getCertificateNumber());
        entity.setIssueDate(dto.getIssueDate());

        boolean statusChanged = false;
        if (newStatus != null && !newStatus.equalsIgnoreCase(oldStatus)) {
            entity.setCertificateStatus(CertificateStatus.valueOf(newStatus.toUpperCase()));
            statusChanged = true;
        }

        CalibrationCertificate updated = repository.save(entity);

        // Send status update email
        if (statusChanged && updated.getServiceRequest() != null) {
            Company company = updated.getServiceRequest().getCompany();
            if (company != null && company.getEmail() != null && !company.getEmail().isEmpty()) {
                String subject = "Certificate Status Updated – SunTech Calibration Laboratory";
                String body = "Dear " + company.getContactPerson() + ",\n\n" +
                        "The status of your calibration certificate (Certificate Number: " + updated.getCertificateNumber() + ") " +
                        "for Service Request " + updated.getServiceRequest().getServiceRequestNumber() + " has been updated.\n" +
                        "Old Status: " + oldStatus + "\n" +
                        "New Status: " + updated.getCertificateStatus() + "\n\n" +
                        "Best regards,\nSunTech Calibration Laboratory";
                email.sendEmail(company.getEmail(), subject, body);
            }
        }

        return mapper.toDTO(updated);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CalibrationCertificateDTO> searchByStatus(String status) {
        return repository.findByCertificateStatus(status)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CalibrationCertificateDTO> searchByCertificateNumber(String certNo) {
        return repository.findByCertificateNumberContainingIgnoreCase(certNo)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CalibrationCertificateDTO> filterByIssueDateRange(LocalDate start, LocalDate end) {
        return repository.findByIssueDateBetween(start, end)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
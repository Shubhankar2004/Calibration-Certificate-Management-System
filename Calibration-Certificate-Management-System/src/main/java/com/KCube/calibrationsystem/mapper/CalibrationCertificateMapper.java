package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.CalibrationCertificateDTO;
import com.KCube.calibrationsystem.enums.CertificateStatus;
import com.KCube.calibrationsystem.model.CalibrationCertificate;
import com.KCube.calibrationsystem.model.ClientMachines;
import com.KCube.calibrationsystem.model.ServiceRequest;
import com.KCube.calibrationsystem.repository.ClientMachineRepository;
import com.KCube.calibrationsystem.repository.ServiceRequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalibrationCertificateMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServiceRequestRepository serviceRequestRepo;

    @Autowired
    private ClientMachineRepository clientMachineRepo;

    public CalibrationCertificateDTO toDTO(CalibrationCertificate entity) {
        CalibrationCertificateDTO dto = modelMapper.map(entity, CalibrationCertificateDTO.class);
        if (entity.getCertificateStatus() != null) {
            dto.setCertificateStatus(entity.getCertificateStatus().name());
        }
        if (entity.getServiceRequest() != null) {
            dto.setServiceRequestId(entity.getServiceRequest().getId());
        }
        if (entity.getClientMachine() != null) {
            dto.setClientMachineId(entity.getClientMachine().getId());
        }
        return dto;
    }

    public CalibrationCertificate toEntity(CalibrationCertificateDTO dto) {
        CalibrationCertificate entity = modelMapper.map(dto, CalibrationCertificate.class);
        if (dto.getCertificateStatus() != null) {
            entity.setCertificateStatus(CertificateStatus.valueOf(dto.getCertificateStatus()));
        }
        if (dto.getServiceRequestId() != null) {
            entity.setServiceRequest(serviceRequestRepo.findById(dto.getServiceRequestId()).orElse(null));
        }
        if (dto.getClientMachineId() != null) {
            entity.setClientMachine(clientMachineRepo.findById(dto.getClientMachineId()).orElse(null));
        }
        return entity;
    }
}





//package com.KCube.calibrationsystem.mapper;
//
//import com.KCube.calibrationsystem.Dtos.CalibrationCertificateDTO;
//import com.KCube.calibrationsystem.model.CalibrationCertificate;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CalibrationCertificateMapper {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public CalibrationCertificateDTO toDTO(CalibrationCertificate entity) {
//        return modelMapper.map(entity, CalibrationCertificateDTO.class);
//    }
//
//    public CalibrationCertificate toEntity(CalibrationCertificateDTO dto) {
//        return modelMapper.map(dto, CalibrationCertificate.class);
//    }
//}
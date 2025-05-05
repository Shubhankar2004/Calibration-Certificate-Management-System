package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.ServiceRequestDTO;
import com.KCube.calibrationsystem.model.ServiceRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ServiceRequestDTO toDTO(ServiceRequest entity) {
        return modelMapper.map(entity, ServiceRequestDTO.class);
    }

    public ServiceRequest toEntity(ServiceRequestDTO dto) {
        return modelMapper.map(dto, ServiceRequest.class);
    }
}

package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.RawDataDTO;
import com.KCube.calibrationsystem.model.RawData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawDataMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RawDataDTO toDTO(RawData entity) {
        return modelMapper.map(entity, RawDataDTO.class);
    }

    public RawData toEntity(RawDataDTO dto) {
        return modelMapper.map(dto, RawData.class);
    }
}

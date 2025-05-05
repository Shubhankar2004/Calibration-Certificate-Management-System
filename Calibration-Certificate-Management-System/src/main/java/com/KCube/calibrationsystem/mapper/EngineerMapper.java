package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.EngineerDTO;
import com.KCube.calibrationsystem.model.Engineer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EngineerMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EngineerDTO toDTO(Engineer entity) {
        return modelMapper.map(entity, EngineerDTO.class);
    }

    public Engineer toEntity(EngineerDTO dto) {
        return modelMapper.map(dto, Engineer.class);
    }
}


package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.ScalesDTO;
import com.KCube.calibrationsystem.model.Scales;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScalesMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ScalesDTO toDTO(Scales entity) {
        return modelMapper.map(entity, ScalesDTO.class);
    }

    public Scales toEntity(ScalesDTO dto) {
        return modelMapper.map(dto, Scales.class);
    }
}

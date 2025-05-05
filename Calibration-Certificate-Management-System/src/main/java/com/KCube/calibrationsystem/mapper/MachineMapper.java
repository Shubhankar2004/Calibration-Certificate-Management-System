package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.MachineDTO;
import com.KCube.calibrationsystem.model.Machine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MachineMapper {

    @Autowired
    private ModelMapper modelMapper;

    public MachineDTO toDTO(Machine entity) {
        return modelMapper.map(entity, MachineDTO.class);
    }

    public Machine toEntity(MachineDTO dto) {
        return modelMapper.map(dto, Machine.class);
    }
}
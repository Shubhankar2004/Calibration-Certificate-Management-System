package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.TestBlockDTO;
import com.KCube.calibrationsystem.model.TestBlocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBlockMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TestBlockDTO toDTO(TestBlocks entity) {
        return modelMapper.map(entity, TestBlockDTO.class);
    }

    public TestBlocks toEntity(TestBlockDTO dto) {
    	 TestBlocks testBlock = modelMapper.map(dto, TestBlocks.class);
    	if (dto.getCalibratedBy() != null) {
            testBlock.setCalibratedBy(dto.getCalibratedBy());
        }
        
        if (dto.getValidUpto() != null) {
            testBlock.setValidUpto(dto.getValidUpto());
        }
        
        return testBlock;
    }
}

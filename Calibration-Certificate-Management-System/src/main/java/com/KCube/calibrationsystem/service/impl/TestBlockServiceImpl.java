package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.TestBlockDTO;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.TestBlockMapper;
import com.KCube.calibrationsystem.model.TestBlocks;
import com.KCube.calibrationsystem.repository.TestBlockRepository;
import com.KCube.calibrationsystem.service.TestBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestBlockServiceImpl implements TestBlockService {

    @Autowired
    private TestBlockRepository repository;

    @Autowired
    private TestBlockMapper mapper;

    @Override
    public TestBlockDTO create(TestBlockDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public TestBlockDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test block not found with ID: " + id)));
    }

    @Override
    public List<TestBlockDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TestBlockDTO update(Long id, TestBlockDTO dto) {
        TestBlocks block = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test block not found with ID: " + id));

        block.setName(dto.getName());
        block.setMake(dto.getMake());
        block.setIdentificationNumber(dto.getIdentificationNumber());
        block.setRangeValue(dto.getRangeValue());
        block.setProperty(dto.getProperty());
        block.setMeasurementUncertainty(dto.getMeasurementUncertainty());
        block.setNonUniformity(dto.getNonUniformity());
        return mapper.toDTO(repository.save(block));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TestBlockDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TestBlockDTO> searchByProperty(String property) {
        return repository.findByPropertyContainingIgnoreCase(property)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @Override
    public List<TestBlockDTO> getByScale(Long scaleId) {
        List<TestBlocks> blocks = repository.findByScaleId(scaleId);
        return blocks.stream().map(mapper::toDTO).toList();
    }

}
package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.ScalesDTO;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.ScalesMapper;
import com.KCube.calibrationsystem.model.Scales;
import com.KCube.calibrationsystem.repository.ScalesRepository;
import com.KCube.calibrationsystem.service.ScalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScalesServiceImpl implements ScalesService {

    @Autowired
    private ScalesRepository repository;

    @Autowired
    private ScalesMapper mapper;

    @Override
    public ScalesDTO create(ScalesDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ScalesDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scale not found with ID: " + id)));
    }

    @Override
    public List<ScalesDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ScalesDTO update(Long id, ScalesDTO dto) {
        Scales scale = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scale not found with ID: " + id));

        scale.setScaleName(dto.getScaleName());
        //scale.setScaleValue(dto.getScaleValue());
        //scale.setUnit(dto.getUnit());
        return mapper.toDTO(repository.save(scale));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ScalesDTO> searchByName(String name) {
        return repository.findByScaleNameContainingIgnoreCase(name)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScalesDTO> filterByLoadCapacity(int min, int max) {
        return repository.findByLoadCapacityBetween(min, max)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.MachineDTO;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.MachineMapper;
import com.KCube.calibrationsystem.model.Machine;
import com.KCube.calibrationsystem.repository.MachineRepository;
import com.KCube.calibrationsystem.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineRepository repository;

    @Autowired
    private MachineMapper mapper;

    @Override
    public MachineDTO create(MachineDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public MachineDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + id)));
    }

    @Override
    public List<MachineDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MachineDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @Override
    public MachineDTO update(Long id, MachineDTO dto) {
        Machine existing = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id: " + id));

        existing.setName(dto.getName());

        Machine updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    
//
//    @Override
//    public List<MachineDTO> searchByMake(String make) {
//        return repository.findByMakeContainingIgnoreCase(make)
//                .stream().map(mapper::toDTO).collect(Collectors.toList());
//    }

//    @Override
//    public List<MachineDTO> searchByModel(String model) {
//        return repository.findByModelContainingIgnoreCase(model)
//                .stream().map(mapper::toDTO).collect(Collectors.toList());
//    }
}
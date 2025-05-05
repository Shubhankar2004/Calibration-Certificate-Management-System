package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.ClientMachineDTO;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.ClientMachineMapper;
import com.KCube.calibrationsystem.model.ClientMachines;
import com.KCube.calibrationsystem.repository.ClientMachineRepository;
import com.KCube.calibrationsystem.service.ClientMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientMachineServiceImpl implements ClientMachineService {

    @Autowired
    private ClientMachineRepository repository;

    @Autowired
    private ClientMachineMapper mapper;

    @Override
    public ClientMachineDTO create(ClientMachineDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ClientMachineDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client machine not found with ID: " + id)));
    }

    @Override
    public List<ClientMachineDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClientMachineDTO update(Long id, ClientMachineDTO dto) {
        ClientMachines machine = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client machine not found with ID: " + id));
        machine.setIdentificationNumber(dto.getIdentificationNumber());
        machine.setMake(dto.getMake());
        machine.setModel(dto.getModel());
        machine.setSerialNumber(dto.getSerialNumber());
        machine.setAmount(dto.getAmount());
        //machine.setCalibrationPeriod(dto.getCalibrationPeriod());
        return mapper.toDTO(repository.save(machine));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ClientMachineDTO> searchByMake(String make) {
        return repository.findByMakeContainingIgnoreCase(make)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClientMachineDTO> searchByModel(String model) {
        return repository.findByModelContainingIgnoreCase(model)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClientMachineDTO> searchBySerial(String serial) {
        return repository.findBySerialNumberContainingIgnoreCase(serial)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
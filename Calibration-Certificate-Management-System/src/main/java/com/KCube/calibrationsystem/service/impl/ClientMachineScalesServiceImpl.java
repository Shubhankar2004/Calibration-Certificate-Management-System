package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.ScalesDTO;
import com.KCube.calibrationsystem.mapper.ScalesMapper;
import com.KCube.calibrationsystem.model.Scales;
import com.KCube.calibrationsystem.repository.ClientMachineScalesRepository;
import com.KCube.calibrationsystem.service.ClientMachineScalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientMachineScalesServiceImpl implements ClientMachineScalesService {

    @Autowired
    private ClientMachineScalesRepository repository;

    @Autowired
    private ScalesMapper mapper;

//    @Override
//    public List<ScalesDTO> getScalesForClientMachine(Long clientMachineId) {
//        List<Scales> scales = repository.findScalesByClientMachineId(clientMachineId);
//        return scales.stream().map(mapper::toDTO).collect(Collectors.toList());
//    }
}

package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.ScalesDTO;
import com.KCube.calibrationsystem.service.ClientMachineScalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/client-machine-scales")
public class ClientMachineScalesController {

    @Autowired
    private ClientMachineScalesService service;

//    @GetMapping("/{clientMachineId}")
//    public List<ScalesDTO> getScales(@PathVariable Long clientMachineId) {
//        return service.getScalesForClientMachine(clientMachineId);
//    }
}
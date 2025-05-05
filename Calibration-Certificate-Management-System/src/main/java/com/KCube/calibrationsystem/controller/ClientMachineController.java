package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.ClientMachineDTO;
import com.KCube.calibrationsystem.service.ClientMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/client-machines")
public class ClientMachineController {

    @Autowired
    private ClientMachineService service;

    @PostMapping
    public ClientMachineDTO create(@RequestBody ClientMachineDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ClientMachineDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ClientMachineDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ClientMachineDTO update(@PathVariable Long id, @RequestBody ClientMachineDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/make")
    public List<ClientMachineDTO> searchByMake(@RequestParam String make) {
        return service.searchByMake(make);
    }

    @GetMapping("/search/model")
    public List<ClientMachineDTO> searchByModel(@RequestParam String model) {
        return service.searchByModel(model);
    }

    @GetMapping("/search/serial")
    public List<ClientMachineDTO> searchBySerial(@RequestParam String serial) {
        return service.searchBySerial(serial);
    }
}

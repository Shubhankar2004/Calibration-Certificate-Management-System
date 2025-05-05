package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.EngineerDTO;
import com.KCube.calibrationsystem.service.EngineerService;
import com.KCube.calibrationsystem.service.impl.EngineerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/engineers")
public class EngineerController {

    @Autowired
    private EngineerServiceImpl service;

    @PostMapping
    public EngineerDTO create(@RequestBody EngineerDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public EngineerDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<EngineerDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public EngineerDTO update(@PathVariable Long id, @RequestBody EngineerDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

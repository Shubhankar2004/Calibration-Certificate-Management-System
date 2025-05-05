package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.MachineDTO;
import com.KCube.calibrationsystem.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/machines")
public class MachineController {

    @Autowired
    private MachineService service;

    @PostMapping
    public MachineDTO create(@RequestBody MachineDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public MachineDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<MachineDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/name")
    public List<MachineDTO> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }
    
    @PutMapping("/{id}")
    public MachineDTO update(@PathVariable Long id, @RequestBody MachineDTO dto) {
        return service.update(id, dto);
    }


//    @GetMapping("/search/make")
//    public List<MachineDTO> searchByMake(@RequestParam String make) {
//        return service.searchByMake(make);
//    }
//
//    @GetMapping("/search/model")
//    public List<MachineDTO> searchByModel(@RequestParam String model) {
//        return service.searchByModel(model);
//    }
}
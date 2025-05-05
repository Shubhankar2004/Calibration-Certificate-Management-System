package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.TestBlockDTO;
import com.KCube.calibrationsystem.service.TestBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/test-blocks")
public class TestBlockController {

    @Autowired
    private TestBlockService service;

    @PostMapping
    public TestBlockDTO create(@RequestBody TestBlockDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public TestBlockDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<TestBlockDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public TestBlockDTO update(@PathVariable Long id, @RequestBody TestBlockDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/name")
    public List<TestBlockDTO> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    @GetMapping("/search/property")
    public List<TestBlockDTO> searchByProperty(@RequestParam String property) {
        return service.searchByProperty(property);
    }
    
    @GetMapping("/by-scale/{scaleId}")
    public List<TestBlockDTO> getByScale(@PathVariable Long scaleId) {
        System.out.println("Fetching blocks for scaleId: " + scaleId);
        return service.getByScale(scaleId);
    }


}
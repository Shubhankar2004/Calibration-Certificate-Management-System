package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.ScalesDTO;
import com.KCube.calibrationsystem.service.ScalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/scales")
public class ScalesController {

    @Autowired
    private ScalesService service;

    @PostMapping
    public ScalesDTO create(@RequestBody ScalesDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ScalesDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ScalesDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ScalesDTO update(@PathVariable Long id, @RequestBody ScalesDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/name")
    public List<ScalesDTO> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    @GetMapping("/filter/scale-value")
    public List<ScalesDTO> filterByScaleValue(@RequestParam int min,
                                              @RequestParam int max) {
        return service.filterByLoadCapacity(min, max);
    }
}

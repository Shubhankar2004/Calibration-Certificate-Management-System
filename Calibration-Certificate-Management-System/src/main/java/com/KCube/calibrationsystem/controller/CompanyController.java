package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.CompanyDTO;
import com.KCube.calibrationsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @PostMapping
    public CompanyDTO create(@RequestBody CompanyDTO dto) {
        return service.createCompany(dto);
    }

    @GetMapping("/{id}")
    public CompanyDTO getById(@PathVariable Long id) {
        return service.getCompanyById(id);
    }
    
    @GetMapping("/all")
    public List<CompanyDTO> getAll() {
        return service.getAllCompanies();
    }

    @PutMapping("/{id}")
    public CompanyDTO update(@PathVariable Long id, @RequestBody CompanyDTO dto) {
        return service.updateCompany(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCompany(id);
    }

    @GetMapping("/search")
    public List<CompanyDTO> search(@RequestParam String name) {
        return service.searchByName(name);
    }
}
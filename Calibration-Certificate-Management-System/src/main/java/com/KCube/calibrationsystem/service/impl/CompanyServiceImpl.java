package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.CompanyDTO;

import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.CompanyMapper;
import com.KCube.calibrationsystem.model.Company;
import com.KCube.calibrationsystem.repository.CompanyRepository;
import com.KCube.calibrationsystem.service.CompanyService;
import com.KCube.calibrationsystem.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private CompanyMapper mapper;
    
    @Autowired
    private EmailService email;

    @Override
    public CompanyDTO createCompany(CompanyDTO dto) {
        Company company = mapper.toEntity(dto);
        Company savedCompany = repository.save(company);

        // Send welcome email
        if (savedCompany.getEmail() != null && !savedCompany.getEmail().isEmpty()) {
            String subject = "Welcome to SunTech Calibration Laboratory";
            String body = "Dear " + savedCompany.getContactPerson() + ",\n\n" +
                          "We are pleased to inform you that your company profile has been successfully created with SunTech Calibration Laboratory.\n" +
                          "We are excited to have " + savedCompany.getName() + " as our valued client.\n\n" +
                          "Regards,\nSunTech Team";
            email.sendEmail(savedCompany.getEmail(), subject, body);
        }

        return mapper.toDTO(savedCompany);
    }


    @Override
    public CompanyDTO getCompanyById(Long id) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
        return mapper.toDTO(company);
    }
    
    @Override
    public List<CompanyDTO> getAllCompanies() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO dto) {
        Company existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setGstNo(dto.getGstNo());
        existing.setContactPerson(dto.getContactPerson());

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void deleteCompany(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<CompanyDTO> searchByName(String name) {
        return repository.findAll()
                .stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

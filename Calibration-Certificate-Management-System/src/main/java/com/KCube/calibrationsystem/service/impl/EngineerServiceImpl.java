package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.EngineerDTO;

import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.EngineerMapper;
import com.KCube.calibrationsystem.model.Engineer;
import com.KCube.calibrationsystem.repository.EngineerRepository;
import com.KCube.calibrationsystem.service.EmailService;
import com.KCube.calibrationsystem.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EngineerServiceImpl implements EngineerService {

    @Autowired
    private EngineerRepository repository;

    @Autowired
    private EngineerMapper mapper;
    
    @Autowired
    private EmailService email;
    
    @Override
    public EngineerDTO create(EngineerDTO dto){
        // Save the user
        Engineer savedEngineer = repository.save(mapper.toEntity(dto));
        
        // Send welcome email
        if (savedEngineer.getEmail() != null && !savedEngineer.getEmail().isEmpty()) {
            String subject = "Welcome to SunTech Calibration Laboratory";
            String body = "Dear " + savedEngineer.getName() + ",\n\n" +
                          "Your profile has been successfully created at SunTech Calibration Laboratory.\n" +
                          "We are glad to have you with us.\n\n" +
                          "Best regards,\n" +
                          "SunTech Team";
            email.sendEmail(savedEngineer.getEmail(), subject, body);
        }

        return mapper.toDTO(savedEngineer);
    }

    @Override
    public EngineerDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found with ID: " + id)));
    }

    @Override
    public List<EngineerDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public EngineerDTO update(Long id, EngineerDTO dto) {
        Engineer engineer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found with ID: " + id));

        engineer.setName(dto.getName());
        return mapper.toDTO(repository.save(engineer));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

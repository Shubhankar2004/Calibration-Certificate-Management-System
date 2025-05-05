package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.UserDTO;

import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.UserMapper;
import com.KCube.calibrationsystem.model.User;
import com.KCube.calibrationsystem.repository.UserRepository;
import com.KCube.calibrationsystem.service.EmailService;
import com.KCube.calibrationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;
    
    @Autowired
    private EmailService email;

    @Override
    public UserDTO create(UserDTO dto) {
        // Save the user
        User savedUser = repository.save(mapper.toEntity(dto));
        
        // Send welcome email
        if (savedUser.getEmail() != null && !savedUser.getEmail().isEmpty()) {
            String subject = "Welcome to SunTech Calibration Laboratory";
            String body = "Dear " + savedUser.getUsername() + ",\n\n" +
                          "Your profile has been successfully created at SunTech Calibration Laboratory.\n" +
                          "We are glad to have you with us.\n\n" +
                          "Best regards,\n" +
                          "SunTech Team";
            email.sendEmail(savedUser.getEmail(), subject, body);
        }

        return mapper.toDTO(savedUser);
    }


    @Override
    public UserDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id)));
    }

    @Override
    public List<UserDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setUsername(dto.getUsername());
        //user.setRole(dto.getRole());
        return mapper.toDTO(repository.save(user));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDTO> searchByUsername(String username) {
        return repository.findAll().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
                .map(mapper::toDTO).collect(Collectors.toList());
    }
}
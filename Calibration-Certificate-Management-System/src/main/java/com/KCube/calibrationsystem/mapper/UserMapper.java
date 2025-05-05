package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.UserDTO;
import com.KCube.calibrationsystem.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}
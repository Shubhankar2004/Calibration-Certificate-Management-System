package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.CompanyDTO;
import com.KCube.calibrationsystem.model.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CompanyDTO toDTO(Company company) {
        return modelMapper.map(company, CompanyDTO.class);
    }

    public Company toEntity(CompanyDTO dto) {
        return modelMapper.map(dto, Company.class);
    }
}
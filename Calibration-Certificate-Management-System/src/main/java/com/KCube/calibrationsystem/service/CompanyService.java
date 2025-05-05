package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.CompanyDTO;
import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO dto);
    CompanyDTO getCompanyById(Long id);
    List<CompanyDTO> getAllCompanies();
    CompanyDTO updateCompany(Long id, CompanyDTO dto);
    void deleteCompany(Long id);
    List<CompanyDTO> searchByName(String name);
}

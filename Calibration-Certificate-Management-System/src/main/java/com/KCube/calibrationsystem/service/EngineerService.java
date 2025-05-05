package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.EngineerDTO;
import java.util.List;

public interface EngineerService {
    EngineerDTO create(EngineerDTO dto);
    EngineerDTO getById(Long id);
    List<EngineerDTO> getAll();
    EngineerDTO update(Long id, EngineerDTO dto);
    void delete(Long id);
}
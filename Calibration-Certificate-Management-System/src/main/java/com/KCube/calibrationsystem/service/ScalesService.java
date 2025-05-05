package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.ScalesDTO;
import java.util.List;

public interface ScalesService {
    ScalesDTO create(ScalesDTO dto);
    ScalesDTO getById(Long id);
    List<ScalesDTO> getAll();
    ScalesDTO update(Long id, ScalesDTO dto);
    void delete(Long id);
    List<ScalesDTO> searchByName(String name);
    List<ScalesDTO> filterByLoadCapacity(int min, int max);
}

package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.TestBlockDTO;
import java.util.List;

public interface TestBlockService {
    TestBlockDTO create(TestBlockDTO dto);
    TestBlockDTO getById(Long id);
    List<TestBlockDTO> getAll();
    TestBlockDTO update(Long id, TestBlockDTO dto);
    void delete(Long id);
    List<TestBlockDTO> searchByName(String name);
    List<TestBlockDTO> searchByProperty(String property);
    List<TestBlockDTO> getByScale(Long scaleId);

}
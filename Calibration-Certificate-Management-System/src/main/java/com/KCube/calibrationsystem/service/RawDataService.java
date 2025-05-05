package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.RawDataDTO;
import java.util.List;

public interface RawDataService {
    RawDataDTO create(RawDataDTO dto);
    RawDataDTO getById(Long id);
    List<RawDataDTO> getAll();
    RawDataDTO update(Long id, RawDataDTO dto);
    void delete(Long id);
    List<RawDataDTO> searchByCalibrationType(String type);
    List<RawDataDTO> searchByIndentorChecked(String value);
    List<RawDataDTO> filterByRange(float min, float max);
    RawDataDTO getRawDataByClientMachineId(Long clientMachineId);
}
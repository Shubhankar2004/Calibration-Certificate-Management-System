package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.MachineDTO;
import java.util.List;

public interface MachineService {
    MachineDTO create(MachineDTO dto);
    MachineDTO getById(Long id);
    List<MachineDTO> getAll();
    void delete(Long id);
    List<MachineDTO> searchByName(String name);
    MachineDTO update(Long id, MachineDTO dto);

//    List<MachineDTO> searchByMake(String make);
//    List<MachineDTO> searchByModel(String model);
}

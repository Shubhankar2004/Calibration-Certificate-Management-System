package com.KCube.calibrationsystem.service;
import com.KCube.calibrationsystem.Dtos.ClientMachineDTO;
import java.util.List;

public interface ClientMachineService {
    ClientMachineDTO create(ClientMachineDTO dto);
    ClientMachineDTO getById(Long id);
    List<ClientMachineDTO> getAll();
    ClientMachineDTO update(Long id, ClientMachineDTO dto);
    void delete(Long id);
    List<ClientMachineDTO> searchByMake(String make);
    List<ClientMachineDTO> searchByModel(String model);
    List<ClientMachineDTO> searchBySerial(String serial);
}

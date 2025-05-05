package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.ServiceRequestDTO;
import java.time.LocalDate;
import java.util.List;

public interface ServiceRequestService {
    ServiceRequestDTO create(ServiceRequestDTO dto);
    ServiceRequestDTO getById(Long id);
    List<ServiceRequestDTO> getAll();
    ServiceRequestDTO update(Long id, ServiceRequestDTO dto);
    void delete(Long id);
    List<ServiceRequestDTO> searchByCalibrationType(String type);
    List<ServiceRequestDTO> searchByRequestNumber(String number);
    List<ServiceRequestDTO> filterByDateRange(LocalDate start, LocalDate end);
}
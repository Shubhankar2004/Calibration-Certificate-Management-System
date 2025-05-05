package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.ServiceRequestDTO;
import com.KCube.calibrationsystem.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/auth/api/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService service;

    @PostMapping
    public ServiceRequestDTO create(@RequestBody ServiceRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ServiceRequestDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ServiceRequestDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ServiceRequestDTO update(@PathVariable Long id, @RequestBody ServiceRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/calibration-type")
    public List<ServiceRequestDTO> searchByCalibrationType(@RequestParam String type) {
        return service.searchByCalibrationType(type);
    }

    @GetMapping("/search/request-number")
    public List<ServiceRequestDTO> searchByRequestNumber(@RequestParam String number) {
        return service.searchByRequestNumber(number);
    }

    @GetMapping("/filter/date-range")
    public List<ServiceRequestDTO> filterByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.filterByDateRange(start, end);
    }
}

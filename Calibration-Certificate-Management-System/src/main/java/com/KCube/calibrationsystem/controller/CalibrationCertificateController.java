package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.CalibrationCertificateDTO;
import com.KCube.calibrationsystem.model.CalibrationCertificate;
import com.KCube.calibrationsystem.repository.CalibrationCertificateRepository;
import com.KCube.calibrationsystem.service.impl.CalibrationCertificateServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/auth/api/certificates")
public class CalibrationCertificateController {

    @Autowired
    private CalibrationCertificateServiceImpl service;
    
    @Autowired
    private CalibrationCertificateRepository certificateRepo;

    @PostMapping
    public CalibrationCertificateDTO create(@RequestBody CalibrationCertificateDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public CalibrationCertificateDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CalibrationCertificateDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public CalibrationCertificateDTO update(@PathVariable Long id, @RequestBody CalibrationCertificateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/status")
    public List<CalibrationCertificateDTO> searchByStatus(@RequestParam String status) {
        return service.searchByStatus(status);
    }

    @GetMapping("/search/certificate-number")
    public List<CalibrationCertificateDTO> searchByCertificateNumber(@RequestParam String certNo) {
        return service.searchByCertificateNumber(certNo);
    }

    @GetMapping("/filter/date-range")
    public List<CalibrationCertificateDTO> filterByIssueDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.filterByIssueDateRange(start, end);
    }
    
    
    @PostMapping("/{id}/generate")
    public String generateCertificate(@PathVariable Long id) {
        try {
            CalibrationCertificate cert = service.generateCertificate(id);
            return "Certificate generated successfully.\nFile path: " + cert.getExcelFilePath();
        } catch (Exception e) {
            return "Error generating certificate: " + e.getMessage();
        }
    }

    @GetMapping("/{id}/download")
    public @ResponseBody byte[] downloadCertificate(@PathVariable Long id) throws IOException {
        CalibrationCertificate cert = certificateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found with ID: " + id));

        String filePath = cert.getExcelFilePath();
        if (filePath == null || filePath.isBlank()) {
            throw new FileNotFoundException("Certificate file path is empty.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Certificate file not found on disk.");
        }

        return Files.readAllBytes(file.toPath());
    }

}
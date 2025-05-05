package com.KCube.calibrationsystem.service;

import com.KCube.calibrationsystem.Dtos.CalibrationCertificateDTO;
import java.time.LocalDate;
import java.util.List;

public interface CalibrationCertificateService {
    CalibrationCertificateDTO create(CalibrationCertificateDTO dto);
    CalibrationCertificateDTO getById(Long id);
    List<CalibrationCertificateDTO> getAll();
    CalibrationCertificateDTO update(Long id, CalibrationCertificateDTO dto);
    void delete(Long id);
    List<CalibrationCertificateDTO> searchByStatus(String status);
    List<CalibrationCertificateDTO> searchByCertificateNumber(String certNo);
    List<CalibrationCertificateDTO> filterByIssueDateRange(LocalDate start, LocalDate end);
}

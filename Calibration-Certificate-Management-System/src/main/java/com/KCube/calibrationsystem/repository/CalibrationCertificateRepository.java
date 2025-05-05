package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.CalibrationCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalibrationCertificateRepository extends JpaRepository<CalibrationCertificate, Long> {
    List<CalibrationCertificate> findByCertificateStatus(String status);
    List<CalibrationCertificate> findByCertificateNumberContainingIgnoreCase(String certNumber);
    List<CalibrationCertificate> findByIssueDateBetween(LocalDate start, LocalDate end);
}
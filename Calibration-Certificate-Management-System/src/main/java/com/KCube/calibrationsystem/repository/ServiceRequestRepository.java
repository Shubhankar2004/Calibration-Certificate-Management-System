package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByCalibrationType(String calibrationType);
    List<ServiceRequest> findByServiceRequestNumberContainingIgnoreCase(String requestNumber);
    List<ServiceRequest> findByRequestDateBetween(LocalDate start, LocalDate end);
}

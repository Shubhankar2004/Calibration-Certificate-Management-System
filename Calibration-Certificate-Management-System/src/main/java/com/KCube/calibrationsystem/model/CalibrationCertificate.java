package com.KCube.calibrationsystem.model;

import java.time.LocalDate;

import com.KCube.calibrationsystem.enums.CertificateStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calibration_certificates")
public class CalibrationCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificateNumber;   // A unique identifier for the certificate
    private LocalDate issueDate;    // Date the certificate was generated
    private LocalDate expiryDate;   // Date when calibration is due for renewal
    private String excelFilePath; 
    
    @Enumerated(EnumType.STRING)
    private CertificateStatus certificateStatus;
    
    // You might store references to the raw data or even embed it in a separate entity
    // private String rawDataPath; // If you store a PDF or file path

    // A certificate is associated with exactly one scale
//    @ManyToOne
//    @JoinColumn(name = "scale_id")
//    private Scales scale;

    // The certificate might also reference the contract or the order it stems from
    @ManyToOne
    @JoinColumn(name = "serviceRequest_id")
    private ServiceRequest serviceRequest;
    
    @OneToOne
    @JoinColumn(name = "clientMachine_id")
    private ClientMachines clientMachine;
    
//    @ManyToOne
//    @JoinColumn(name = "engineer_id")
//    private Engineer engineer;

    // Constructors, getters, setters...
}


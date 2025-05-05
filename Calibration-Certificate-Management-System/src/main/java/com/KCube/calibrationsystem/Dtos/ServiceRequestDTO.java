package com.KCube.calibrationsystem.Dtos;

import java.time.LocalDate;

import com.KCube.calibrationsystem.enums.CalibrationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDTO {
    private Long id;
    private String serviceRequestNumber;
    private LocalDate requestDate;
    private String contactPerson;
    private String contactNumber;
    private String email;
    private Boolean requiredCertificatesUnderNabl;
    private int frequencyRequired;
    private CalibrationType calibrationType;
    private String certificatesSentBy;
    private Boolean calibrationAsPerStandard;
    private Boolean repairingRequired;
    private Boolean calibrationScopeAccepted;
    private Boolean conformityStatementRequired;
    private Boolean commercialTermsAccepted;
    private Boolean nextCalibrationDateRequired;
    private LocalDate agreedDateForCalibration;
    private int validityPeriod;
    private String status;
    private Long companyId;
    private Long engineerId;
}

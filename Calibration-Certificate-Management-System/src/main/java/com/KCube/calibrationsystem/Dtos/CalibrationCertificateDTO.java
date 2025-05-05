package com.KCube.calibrationsystem.Dtos;

import java.time.LocalDate;
import java.util.List;

import com.KCube.calibrationsystem.enums.CertificateStatus;
import com.KCube.calibrationsystem.model.ClientMachines;
import com.KCube.calibrationsystem.model.Scales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalibrationCertificateDTO {
    private Long id;
    private String certificateNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String certificateStatus;
    private Long serviceRequestId;
    private Long clientMachineId;
}
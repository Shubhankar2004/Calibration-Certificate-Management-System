package com.KCube.calibrationsystem.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientMachineDTO {
    private Long id;
    private String make;
    private String model;
    private String serialNumber;
    private String identificationNumber;
    private String calibrationPeriod;
    private Double amount;
    private Long machineId;
    private Long serviceRequestId;
    private List<Long> selectedScaleIds;
}

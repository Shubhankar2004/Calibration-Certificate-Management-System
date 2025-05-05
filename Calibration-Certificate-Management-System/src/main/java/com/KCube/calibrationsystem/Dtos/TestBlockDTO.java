package com.KCube.calibrationsystem.Dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestBlockDTO {

    private Long id;
    private String name;
    private String make;
    private String identificationNumber;
    private float rangeValue;
    private String property;
    private float measurementUncertainty;
    private float allowedError;
    private float nonUniformity;
    private String calibratedBy; // ✅ This must be here
    private LocalDate validUpto;
    private Long scaleId;
}

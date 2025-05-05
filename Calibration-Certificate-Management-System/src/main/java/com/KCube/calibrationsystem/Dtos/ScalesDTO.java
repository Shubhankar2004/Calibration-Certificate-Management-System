package com.KCube.calibrationsystem.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScalesDTO {
    private Long id;
    private String scaleName;
    private int loadCapacity;
    private String verificationType;
    private float uncertainty;
    private float allowedError;
    private float allowedReadability;
    private Long machineId;
}


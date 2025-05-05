package com.KCube.calibrationsystem.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientMachineDetailDTO extends ClientMachineDTO {
    private MachineDTO machine;
    private List<ScalesDTO> selectedScales;
    private RawDataDTO rawData;
    private CalibrationCertificateDTO certificate;
}

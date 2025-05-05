package com.KCube.calibrationsystem.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDetailDTO extends ServiceRequestDTO {
    private CompanyDTO company;
    private EngineerDTO engineer;
    private List<ClientMachineDetailDTO> clientMachines;
    private List<CalibrationCertificateDTO> certificates;
}

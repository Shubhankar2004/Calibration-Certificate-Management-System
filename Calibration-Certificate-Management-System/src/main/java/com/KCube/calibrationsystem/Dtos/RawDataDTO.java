package com.KCube.calibrationsystem.Dtos;

import com.KCube.calibrationsystem.enums.CalibrationType;
import com.KCube.calibrationsystem.enums.InspectionStatus;
import com.KCube.calibrationsystem.enums.EquipmentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawDataDTO {
    private Long id;

    private CalibrationType calibrationType;
    private String rawData;
    private LocalDate serviceDate;

    private String leastCount;
    private float ranges;
    private String dialGaugeNo;
    private String indenterNo;
    private float temperatureDetail;

    // Machine-level inspection: YES/NO (mapped to InspectionStatus Enum)
    private InspectionStatus machineLeveled;
    private InspectionStatus indenterGoodCondition;
    private InspectionStatus cleanAnvil;
    private InspectionStatus vibrationFree;
    private InspectionStatus dialGaugeZero;
    private InspectionStatus standardBlocksCalibrated;
    private InspectionStatus magnifierClean;
    private InspectionStatus tempHumidityOk;
    private InspectionStatus lightingAdequate;
    private InspectionStatus loadSelectorWorking;

    // Equipment-level inspection: OK/NOT_OK (mapped to EquipmentStatus Enum)
    private EquipmentStatus indentorChecked;
    private EquipmentStatus surfaceChecked;
    private EquipmentStatus testBlockFlatness;
    private EquipmentStatus testBlockLabelVisible;
    private EquipmentStatus dialGaugeCheck;
    private EquipmentStatus testBlockCalDueDateChecked;

    private Long clientMachineId;
}

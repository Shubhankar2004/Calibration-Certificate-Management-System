package com.KCube.calibrationsystem.model;

import java.time.LocalDate;
import java.util.List;

import com.KCube.calibrationsystem.enums.CalibrationType;
import com.KCube.calibrationsystem.enums.EquipmentStatus;
import com.KCube.calibrationsystem.enums.InspectionStatus;
import com.KCube.calibrationsystem.enums.Verification;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "raw_data")
public class RawData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CalibrationType calibrationType;

    @Lob
    private String rawData;

    private LocalDate serviceDate;
    private String leastCount;
    private float ranges;
    private String dialGaugeNo;
    private String indenterNo;
    private float temperatureDetail;

    // === Machine Inspection (10 fixed checkpoints) ===
    @Enumerated(EnumType.STRING)
    private InspectionStatus machineLeveled;

    @Enumerated(EnumType.STRING)
    private InspectionStatus indenterGoodCondition;

    @Enumerated(EnumType.STRING)
    private InspectionStatus cleanAnvil;

    @Enumerated(EnumType.STRING)
    private InspectionStatus vibrationFree;

    @Enumerated(EnumType.STRING)
    private InspectionStatus dialGaugeZero;

    @Enumerated(EnumType.STRING)
    private InspectionStatus standardBlocksCalibrated;

    @Enumerated(EnumType.STRING)
    private InspectionStatus magnifierClean;

    @Enumerated(EnumType.STRING)
    private InspectionStatus tempHumidityOk;

    @Enumerated(EnumType.STRING)
    private InspectionStatus lightingAdequate;

    @Enumerated(EnumType.STRING)
    private InspectionStatus loadSelectorWorking;

    // === Equipment / Test Block Checks (6 fixed items) ===
    @Enumerated(EnumType.STRING)
    private EquipmentStatus indentorChecked;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus surfaceChecked;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus testBlockFlatness;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus testBlockLabelVisible;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus dialGaugeCheck;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus testBlockCalDueDateChecked;

    @OneToOne
    @JoinColumn(name = "clientMachine_id")
    private ClientMachines clientMachine;
}



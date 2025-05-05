package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.RawDataDTO;
import com.KCube.calibrationsystem.enums.CalibrationType;
import com.KCube.calibrationsystem.enums.EquipmentStatus;
import com.KCube.calibrationsystem.enums.InspectionStatus;
import com.KCube.calibrationsystem.exception.GlobalExceptionHandler;
import com.KCube.calibrationsystem.exception.RawDataAlreadyExists;
import com.KCube.calibrationsystem.mapper.RawDataMapper;
import com.KCube.calibrationsystem.model.RawData;
import com.KCube.calibrationsystem.repository.ClientMachineRepository;
import com.KCube.calibrationsystem.repository.RawDataRepository;
import com.KCube.calibrationsystem.service.RawDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth/api/raw-data")
public class RawDataController {

    @Autowired
    private RawDataService service;

    @Autowired
    private RawDataRepository rawDataRepo;

    @Autowired
    private ClientMachineRepository clientMachineRepo;

    @Autowired
    private RawDataMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<?> saveRawData(@RequestBody Map<String, Object> request) {
        try {
            RawData rawData = new RawData();

            Long clientMachineId = Long.parseLong(request.get("clientMachineId").toString());
            rawData.setClientMachine(clientMachineRepo.findById(clientMachineId)
                    .orElseThrow(() -> new IllegalArgumentException("Client Machine not found")));

            rawData.setCalibrationType(CalibrationType.valueOf(request.get("calibrationType").toString()));
            rawData.setServiceDate(LocalDate.parse(request.get("serviceDate").toString()));
            rawData.setRawData(objectMapper.valueToTree(request.get("rawData")).toString());

            rawData.setMachineLeveled(getInspectionStatus(request, "machineLeveled"));
            rawData.setIndenterGoodCondition(getInspectionStatus(request, "indenterGoodCondition"));
            rawData.setCleanAnvil(getInspectionStatus(request, "cleanAnvil"));
            rawData.setVibrationFree(getInspectionStatus(request, "vibrationFree"));
            rawData.setDialGaugeZero(getInspectionStatus(request, "dialGaugeZero"));
            rawData.setStandardBlocksCalibrated(getInspectionStatus(request, "standardBlocksCalibrated"));
            rawData.setMagnifierClean(getInspectionStatus(request, "magnifierClean"));
            rawData.setTempHumidityOk(getInspectionStatus(request, "tempHumidityOk"));
            rawData.setLightingAdequate(getInspectionStatus(request, "lightingAdequate"));
            rawData.setLoadSelectorWorking(getInspectionStatus(request, "loadSelectorWorking"));

            rawData.setIndentorChecked(getEquipmentStatus(request, "indentorChecked"));
            rawData.setSurfaceChecked(getEquipmentStatus(request, "surfaceChecked"));
            rawData.setTestBlockFlatness(getEquipmentStatus(request, "testBlockFlatness"));
            rawData.setTestBlockLabelVisible(getEquipmentStatus(request, "testBlockLabelVisible"));
            rawData.setDialGaugeCheck(getEquipmentStatus(request, "dialGaugeCheck"));
            rawData.setTestBlockCalDueDateChecked(getEquipmentStatus(request, "testBlockCalDueDateChecked"));

            rawData.setLeastCount(request.get("leastCount").toString());
            rawData.setIndenterNo(request.get("indenterNo").toString());
            rawData.setDialGaugeNo(request.get("dialGaugeNo").toString());
            rawData.setRanges(Float.parseFloat(request.get("ranges").toString()));
            rawData.setTemperatureDetail(Float.parseFloat(request.get("temperatureDetail").toString()));

          
            // Save and return
            rawData = rawDataRepo.save(rawData);
            return ResponseEntity.ok(mapper.toDTO(rawData));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while saving raw data: " + e.getMessage());
        }
    }

    private InspectionStatus getInspectionStatus(Map<String, Object> request, String key) {
        try {
            return InspectionStatus.valueOf(request.get(key).toString().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    private EquipmentStatus getEquipmentStatus(Map<String, Object> request, String key) {
        try {
            return EquipmentStatus.valueOf(request.get(key).toString().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/client-machine/{clientMachineId}")
    public RawDataDTO getRawDataByClientMachine(@PathVariable Long clientMachineId) {
    	
        return service.getRawDataByClientMachineId(clientMachineId);
    }
    
    @GetMapping("/{id}")
    public RawDataDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<RawDataDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public RawDataDTO update(@PathVariable Long id, @RequestBody RawDataDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search/calibration-type")
    public List<RawDataDTO> searchByCalibrationType(@RequestParam String type) {
        return service.searchByCalibrationType(type);
    }

    @GetMapping("/search/indenter-checked")
    public List<RawDataDTO> searchByIndenterChecked(@RequestParam String value) {
        return service.searchByIndentorChecked(value);
    }

    @GetMapping("/filter/range")
    public List<RawDataDTO> filterByRange(@RequestParam float min,
                                          @RequestParam float max) {
        return service.filterByRange(min, max);
    }
}

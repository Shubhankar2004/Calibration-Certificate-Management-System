package com.KCube.calibrationsystem.mapper;

import com.KCube.calibrationsystem.Dtos.ClientMachineDTO;
import com.KCube.calibrationsystem.model.ClientMachines;
import com.KCube.calibrationsystem.model.Machine;
import com.KCube.calibrationsystem.model.Scales;
import com.KCube.calibrationsystem.model.ServiceRequest;
import com.KCube.calibrationsystem.repository.MachineRepository;
import com.KCube.calibrationsystem.repository.ScalesRepository;
import com.KCube.calibrationsystem.repository.ServiceRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientMachineMapper {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ScalesRepository scaleRepository;

    public ClientMachineDTO toDTO(ClientMachines entity) {
        ClientMachineDTO dto = new ClientMachineDTO();
        dto.setId(entity.getId());
        dto.setMake(entity.getMake());
        dto.setModel(entity.getModel());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setIdentificationNumber(entity.getIdentificationNumber());
        //dto.setCalibrationPeriod(entity.getCalibrationPeriod());
        dto.setAmount(entity.getAmount());

        if (entity.getMachines() != null)
            dto.setMachineId(entity.getMachines().getId());

        if (entity.getServiceRequest() != null)
            dto.setServiceRequestId(entity.getServiceRequest().getId());

        if (entity.getSelectedScales() != null)
            dto.setSelectedScaleIds(entity.getSelectedScales().stream().map(Scales::getId).toList());

        return dto;
    }

    public ClientMachines toEntity(ClientMachineDTO dto) {
        ClientMachines clientMachine = new ClientMachines();
        clientMachine.setMake(dto.getMake());
        clientMachine.setModel(dto.getModel());
        clientMachine.setSerialNumber(dto.getSerialNumber());
        clientMachine.setIdentificationNumber(dto.getIdentificationNumber());
        //clientMachine.setCalibrationPeriod(dto.getCalibrationPeriod());
        clientMachine.setAmount(dto.getAmount());

        // Set Machine entity
        Machine machine = machineRepository.findById(dto.getMachineId())
                .orElseThrow(() -> new RuntimeException("Machine not found"));
        clientMachine.setMachines(machine);

        // Set ServiceRequest entity
        ServiceRequest serviceRequest = serviceRequestRepository.findById(dto.getServiceRequestId())
                .orElseThrow(() -> new RuntimeException("Service Request not found"));
        clientMachine.setServiceRequest(serviceRequest);

        // Set Scales
        List<Scales> selectedScales = scaleRepository.findAllById(dto.getSelectedScaleIds());
        clientMachine.setSelectedScales(selectedScales);

        return clientMachine;
    }
}




//package com.KCube.calibrationsystem.mapper;
//import com.KCube.calibrationsystem.Dtos.ClientMachineDTO;
//import com.KCube.calibrationsystem.model.ClientMachines;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ClientMachineMapper {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public ClientMachineDTO toDTO(ClientMachines entity) {
//        return modelMapper.map(entity, ClientMachineDTO.class);
//    }
//
//    public ClientMachines toEntity(ClientMachineDTO dto) {
//        return modelMapper.map(dto, ClientMachines.class);
//    }
//}
//

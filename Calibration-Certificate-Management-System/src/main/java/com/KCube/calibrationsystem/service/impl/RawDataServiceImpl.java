package com.KCube.calibrationsystem.service.impl;

import com.KCube.calibrationsystem.Dtos.RawDataDTO;
import com.KCube.calibrationsystem.exception.ResourceNotFoundException;
import com.KCube.calibrationsystem.mapper.RawDataMapper;
import com.KCube.calibrationsystem.model.ClientMachines;
import com.KCube.calibrationsystem.model.RawData;
import com.KCube.calibrationsystem.repository.ClientMachineRepository;
import com.KCube.calibrationsystem.repository.RawDataRepository;
import com.KCube.calibrationsystem.service.RawDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RawDataServiceImpl implements RawDataService {

    @Autowired
    private RawDataRepository repository;

    @Autowired
    private RawDataMapper mapper;
    
    @Autowired
    private ClientMachineRepository clientMachineRepository;

    @Override
    public RawDataDTO create(RawDataDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public RawDataDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RawData not found with ID: " + id)));
    }

    @Override
    public List<RawDataDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RawDataDTO update(Long id, RawDataDTO dto) {
        RawData entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RawData not found with ID: " + id));

        entity.setCalibrationType(dto.getCalibrationType());
        entity.setRanges(dto.getRanges());
        //entity.setHardness(dto.getHardness());
        entity.setIndentorChecked(dto.getIndentorChecked());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<RawDataDTO> searchByCalibrationType(String type) {
        return repository.findByCalibrationType(type)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RawDataDTO> searchByIndentorChecked(String value) {
        return repository.findByIndentorChecked(value)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RawDataDTO> filterByRange(float min, float max) {
        return repository.findByRangesBetween(min, max)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

	@Override
	public RawDataDTO getRawDataByClientMachineId(Long clientMachineId) {
		 ClientMachines clientMachine = clientMachineRepository.findById(clientMachineId)
	                .orElseThrow(() -> new RuntimeException("Client machine not found with ID: " + clientMachineId));
	     
		RawDataDTO rawData = mapper.toDTO(repository.findByClientMachineId(clientMachine.getId()).get());
		 System.out.println(rawData);
	        return rawData;
	}
}
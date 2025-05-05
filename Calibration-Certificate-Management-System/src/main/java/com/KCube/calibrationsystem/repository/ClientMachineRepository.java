package com.KCube.calibrationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KCube.calibrationsystem.model.ClientMachines;

import com.KCube.calibrationsystem.model.ClientMachines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientMachineRepository extends JpaRepository<ClientMachines, Long> {
    List<ClientMachines> findByMakeContainingIgnoreCase(String make);
    List<ClientMachines> findByModelContainingIgnoreCase(String model);
    List<ClientMachines> findBySerialNumberContainingIgnoreCase(String serialNumber);
}

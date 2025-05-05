package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    List<Machine> findByNameContainingIgnoreCase(String name);
//    List<Machine> findByMakeContainingIgnoreCase(String make);
//    List<Machine> findByModelContainingIgnoreCase(String model);
}
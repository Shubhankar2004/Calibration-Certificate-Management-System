package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.Scales;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientMachineScalesRepository extends CrudRepository<Scales, Long> {
	@Query("SELECT s FROM Scales s WHERE s.machine.id = " +
	           "(SELECT cm.machines.id FROM ClientMachines cm WHERE cm.id = :clientMachineId)")
	    List<Scales> findScalesByClientMachineId(Long clientMachineId);
	//changed 
	
	//List<ClientMachineScales> findByClientMachineId(Long clientMachineId);
	
}
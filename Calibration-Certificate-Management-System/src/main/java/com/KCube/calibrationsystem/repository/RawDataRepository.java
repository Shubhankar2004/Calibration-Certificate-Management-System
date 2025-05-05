package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.RawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RawDataRepository extends JpaRepository<RawData, Long> {
    List<RawData> findByCalibrationType(String type);
    List<RawData> findByIndentorChecked(String indenter);
    List<RawData> findByRangesBetween(float min, float max);
    Optional<RawData> findByClientMachineId(Long clientMachineId);
}

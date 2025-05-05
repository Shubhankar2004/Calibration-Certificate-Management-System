package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.Scales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScalesRepository extends JpaRepository<Scales, Long> {
    List<Scales> findByScaleNameContainingIgnoreCase(String name);
    List<Scales> findByLoadCapacityBetween(int min, int max);
}

package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineerRepository extends JpaRepository<Engineer, Long> {
}


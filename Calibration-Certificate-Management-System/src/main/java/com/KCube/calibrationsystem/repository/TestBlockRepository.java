package com.KCube.calibrationsystem.repository;

import com.KCube.calibrationsystem.model.TestBlocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestBlockRepository extends JpaRepository<TestBlocks, Long> {
    List<TestBlocks> findByNameContainingIgnoreCase(String name);
    List<TestBlocks> findByPropertyContainingIgnoreCase(String property);
    List<TestBlocks> findTop3ByScaleIdOrderByIdAsc(Long scaleId);
    List<TestBlocks> findByScaleId(Long scaleId);

}

package com.KCube.calibrationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KCube.calibrationsystem.model.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{
	boolean existsByGstNo(String gstNo);
    Company findByGstNo(String gstNo);
}

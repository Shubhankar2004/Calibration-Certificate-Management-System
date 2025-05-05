package com.KCube.calibrationsystem.model;

import java.time.LocalDate;
import java.util.List;

import com.KCube.calibrationsystem.enums.Verification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testBlocks")
public class TestBlocks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String make;
	
	private String identificationNumber;
	
	private float rangeValue;
	
	private String property;
	
	private float measurementUncertainty;
	
	private float allowedError;
	
	private float nonUniformity;
	
	private String calibratedBy;
	
	private LocalDate validUpto;
	
	@ManyToOne
    @JoinColumn(name = "scale_id")
	private Scales scale;
	
}

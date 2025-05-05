package com.KCube.calibrationsystem.model;

import java.util.ArrayList;

import java.util.List;

import com.KCube.calibrationsystem.enums.Verification;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "scales")
public class Scales {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

    // e.g., "HRA", "HRB", "HRC", "Vickers HV10", etc.
    private String scaleName;

    // e.g., 15 kg, 30 kg, 45 kg for Rockwell, or 10kg / 1kg for Vickers, etc.
    private int loadCapacity;
    
    @Enumerated(EnumType.STRING)
    private Verification verificationType;
    
    private float uncertainty;
    
    private float allowedReadability;

    // A scale belongs to a single machine
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    
    @OneToMany(mappedBy = "scale", cascade = CascadeType.ALL)
    private List<TestBlocks> testBlocks = new ArrayList<>();

    // One scale can have multiple certificates over time
//    @OneToMany(mappedBy = "scale", cascade = CascadeType.ALL)
//    private List<CalibrationCertificate> certificates = new ArrayList<>();

    // Constructors, getters, setters...
}

package com.KCube.calibrationsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.KCube.calibrationsystem.enums.CalibrationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "engineers")
public class Engineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    // Additional fields (like employeeCode, designation, etc.) can be added if needed.

    // One engineer may perform many service visits
    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

//    // One engineer may generate many calibration certificates
//    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
//    private List<CalibrationCertificate> calibrationCertificates = new ArrayList<>();

//    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
//    private List<OrderAcknowledgement> orders = new ArrayList<>();

}

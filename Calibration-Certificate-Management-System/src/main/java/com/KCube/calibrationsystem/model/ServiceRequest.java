package com.KCube.calibrationsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.KCube.calibrationsystem.enums.CalibrationType;
import com.KCube.calibrationsystem.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "serviceRequests")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceRequestNumber;
    private LocalDate requestDate;
    private String contactPerson;
    private String contactNumber;
    private String email;

    // Basic fields from Contract Form
    private Boolean requiredCertificatesUnderNabl;
    private int frequencyRequired;            // e.g., "3 months", "6 months", "1 year"
    private String certificatesSentBy;           // e.g., "By hand", "By courier"
    private Boolean calibrationAsPerStandard;
    private Boolean repairingRequired;
    private Boolean calibrationScopeAccepted;
    private Boolean conformityStatementRequired;
    private Boolean commercialTermsAccepted;
    private Boolean nextCalibrationDateRequired;
    private LocalDate agreedDateForCalibration;
    
    @Enumerated(EnumType.STRING)
    private CalibrationType calibrationType;

    // E.g. "Validity of contract for One Year"
    private int validityPeriod; // or store as LocalDate if you want an exact end date

    // Relationship to Company
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // A Contract can have multiple OrderAcknowledgements
//    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
//    private List<OrderAcknowledgement> orderAcknowledgements = new ArrayList<>();

    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL)
    private List<CalibrationCertificate> CalibrationCertificate = new ArrayList<>();

    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL)
    private List<ClientMachines>  clientMachines = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private Engineer engineer;

    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;
    
}


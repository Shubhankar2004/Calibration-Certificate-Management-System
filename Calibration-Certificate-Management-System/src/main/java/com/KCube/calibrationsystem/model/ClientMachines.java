package com.KCube.calibrationsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_machines")
public class ClientMachines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String make;
    
    private String model;
    
    private String serialNumber;
    
    private String IdentificationNumber;
    
//    private String calibrationPeriod;
   
    private Double amount;

    // Each ClientMachines links to one Machine (e.g., a specific testing machine)
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machines;

    // Each ClientMachines links back to the Contract under which it is selected
    @ManyToOne
    @JoinColumn(name = "serviceRequest_id")
    private ServiceRequest serviceRequest;

    // A client can select one or more scales from this machine.
    @ManyToMany
    @JoinTable(
        name = "client_machine_scales",
        joinColumns = @JoinColumn(name = "client_machine_id"),
        inverseJoinColumns = @JoinColumn(name = "scale_id")
    )
    private List<Scales> selectedScales = new ArrayList<>();
    
    @OneToOne(mappedBy = "clientMachine")
    private CalibrationCertificate certificate;
    
    @OneToOne(mappedBy = "clientMachine")
    private RawData rawData;
    
//    @OneToOne(mappedBy = "clientMachine",cascade=CascadeType.ALL)
//    private OrderItem orderItem;

    // Optionally, if you need to capture certificate type choices for this machine,
    // you can add a field here (or another relationship if certificate types are modeled as entities)
    // For example:
    // @ElementCollection
    // private List<String> certificateTypes;  // e.g., "Hardness", "Force", "Hardness and Force"
}


















//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="clientMachines")
//public class ClientMachines {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	Long id;
//	
//	private String make;  
//	// e.g., "Tinius Olsen"
//    private String model;   
//    // e.g., "XYZ-123"
//    private String serialNo;
//    
//    private String IdentificationNumber;
//    
//    @ManyToOne
//    @JoinColumn(name = "contract_id")
//    private Contract contract;
//	
//    @OneToOne
//    @JoinColumn(name="ourMachine_Id")
//    private Machine ourMahcines;
//   
//}

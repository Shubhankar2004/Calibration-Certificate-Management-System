//package com.KCube.calibrationsystem.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
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
//@Table(name = "order_items")
//public class OrderItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    private String calibrationPeriod;
//    private Integer quantity;
//    private Double rate;
//    private Double amount;
//
//    // Link to the parent OrderAcknowledgement
////    @ManyToOne
////    @JoinColumn(name = "order_ack_id")
////    private OrderAcknowledgement orderAcknowledgement;
//
//    // Fields from the O.A. table
////    private String machineName;
////    private String make;
////    private String model;
////    private String serialNo;
////    private String scaleMethod;  // e.g., "HRC", "Vickers HV10"
////    @OneToOne
////    @JoinColumn(name="machine_id")
////    private Machine machine;
//    
////    @OneToOne
////    @JoinColumn(name = "client_machine_id")
////    private ClientMachines clientMachine;
//    
////    @ManyToOne
////    @JoinColumn(name="serviceRecordId")
////    private ServiceRecord serviceRecord;
//    
////    @OneToOne(mappedBy = "orderItem")
////    private CalibrationCertificate certificate;
//    
//    
//
//    // Constructors, getters, setters...
//}
//

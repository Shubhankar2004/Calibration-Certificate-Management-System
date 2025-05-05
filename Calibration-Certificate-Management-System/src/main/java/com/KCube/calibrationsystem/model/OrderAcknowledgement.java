//package com.KCube.calibrationsystem.model;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.KCube.calibrationsystem.enums.Role;
//import com.KCube.calibrationsystem.enums.Status;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
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
//@Table(name = "order_acknowledgements")
//public class OrderAcknowledgement {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String orderAckNo;
//    private LocalDate date;
//    private String poNo;           // Purchase Order No.
//
//    // Many acknowledgements can belong to one contract
////    @ManyToOne
////    @JoinColumn(name = "contract_id")
////    private Contract contract;
//
//    // One order can have multiple line items
//    @OneToMany(mappedBy = "orderAcknowledgement", cascade = CascadeType.ALL)
//    private List<OrderItem> orderItems = new ArrayList<>();
//    
//    @ManyToOne
//    @JoinColumn(name = "engineer_id")
//    private Engineer engineer;
//
//    @Enumerated(EnumType.STRING)
//	@Column(nullable = false)
//	private Status status;
//    
//    // Constructors, getters, setters...
//}
//

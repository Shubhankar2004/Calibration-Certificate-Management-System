package com.KCube.calibrationsystem.model;

import java.util.ArrayList;
import java.util.List;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // e.g. "JK Udyog"
    private String address;         // Full address
    private String contactPerson;   // Contact person's name
    private String phone;           // Mobile/phone number
    private String email;
    private String gstNo;

    // One company can have many machines
//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
//    private List<Machine> machines = new ArrayList<>();

    // One company can have many contracts
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    // Constructors, getters, setters...
}

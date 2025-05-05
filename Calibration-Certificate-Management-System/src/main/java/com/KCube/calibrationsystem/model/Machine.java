package com.KCube.calibrationsystem.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "ourMachines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;   // e.g., "Rockwell Hardness Tester"
          // e.g., "SN-001"

    // Relationship to Company
//    @ManyToOne
//    @JoinColumn(name = "company_id")
//    private Company company;

    // A machine can have many scales
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private List<Scales> scales = new ArrayList<>();
    
    @OneToMany(mappedBy = "machines",cascade = CascadeType.ALL)
    private List<ClientMachines> clientMachines;

    // Constructors, getters, setters...
}


package com.KCube.calibrationsystem.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private String gstNo;
    private String contactPerson;
    private String email;
    private String phone;
}

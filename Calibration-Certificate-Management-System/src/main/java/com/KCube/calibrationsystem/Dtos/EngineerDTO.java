package com.KCube.calibrationsystem.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineerDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
package com.KCube.calibrationsystem.model.helper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservedReading {

    private Long scaleId;
    private Long testBlockId;

    // For Rockwell
    private List<Double> readings;

    // For Brinell/Vickers
    private List<IndentationReading> indentations;

    private Double average;
}

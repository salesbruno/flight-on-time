package com.flightontime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayStatsState {
    private String state;        // estado
    private long totalFlights;   // totalVoos
    private double average;      // media
    private double min;          // min
    private double max;          // max
    private double stdDev;       // desvio
}

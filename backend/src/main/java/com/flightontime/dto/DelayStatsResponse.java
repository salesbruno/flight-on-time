package com.flightontime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DelayStatsResponse {
    private LocalDateTime start;
    private LocalDateTime end;
    private long totalVoos;
    private List<Map<String, Object>> resultados;

    private double media;   // média das probabilidades
    private double min;     // menor probabilidade
    private double max;     // maior probabilidade
    private double desvio;  // desvio padrão
}
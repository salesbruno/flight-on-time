package com.flightontime.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VooResponse {
    @JsonProperty("previsao")
    private String prediction;
    @JsonProperty("probabilidade")
    private double probability;
}
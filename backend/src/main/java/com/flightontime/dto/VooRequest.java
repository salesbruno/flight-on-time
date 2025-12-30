package com.flightontime.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VooRequest {

    @NotBlank(message = "Companhia é obrigatória")
    @JsonProperty("companhia")
    private String company;

    @NotBlank(message = "Aeroporto de origem é obrigatório")
    @JsonProperty("origem")
    private String origin;

    @NotBlank(message = "Aeroporto de destino é obrigatório")
    @JsonProperty("destino")
    private String destination;

    @NotNull(message = "Data da Partida é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("data_partida")
    private LocalDateTime departureDate;

    @Min(value = 1, message = "Distância deve ser >= 1")
    @JsonProperty("distancia_km")
    private int distanceKm;

    @NotBlank(message = "Falta Estado de Origem")
    @JsonProperty("estado_origem")
    private String stateOrigin;

    @NotBlank(message = "Falta Estado de Destino")
    @JsonProperty("estado_destino")
    private String stateDestination;
}
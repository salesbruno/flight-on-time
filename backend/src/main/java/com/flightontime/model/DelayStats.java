package com.flightontime.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(name = "flights_historic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelayStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2, nullable = false)
    private String companhia;

    @Column(length = 3, nullable = false)
    private String origem;

    @Column(length = 50, nullable = false)
    private String estadoOrigem;

    @Column(length = 3, nullable = false)
    private String destino;

    @Column(length = 50, nullable = false)
    private String estadoDestino;

    @Column(nullable = false)
    private LocalDateTime dataPartida;

    @Column(nullable = false)
    private Integer distanciaKm;
}
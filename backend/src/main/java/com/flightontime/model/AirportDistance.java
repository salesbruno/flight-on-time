package com.flightontime.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "airports_distance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iata_code_origin", length = 3, nullable = false)
    private String iataCodeOrigin;

    @Column(name = "iata_code_dest", length = 3, nullable = false)
    private String iataCodeDest;

    @Column(name = "distance", nullable = false)
    private Integer distance;
}
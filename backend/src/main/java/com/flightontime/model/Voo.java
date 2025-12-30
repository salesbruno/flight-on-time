package com.flightontime.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "voos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name =  "company", length = 2, nullable = false)
    private String company; // (ex.: AZ)

    @Column(name = "origin", length = 3, nullable = false)
    private String origin;    // IATA (ex.: GIG)

    @Column(name = "destination", length = 3, nullable = false)
    private String destination;   // IATA (ex.: GRU)

    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "distance_km", nullable = false)
    private Integer distanceKM;

    @Column(name = "state_destination", nullable = false)
    private String stateDestitnation;

    @Column(name = "state_origin", nullable = false)
    private String stateOrigin;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

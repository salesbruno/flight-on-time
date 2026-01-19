package com.flightontime.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "airports")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    @Id
    @Column(name = "iata_code", length = 3, nullable = false)
    private String iataCode;

    @Column(name = "airport_name", nullable = false, length = 255)
    private String airportName;

    @Column(name = "city_name", length = 255)
    private String cityName;

    @Column(name = "state_name", length = 255)
    private String stateName;

    @Column(name = "country_name", length = 255)
    private String countryName;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "active")
    private Boolean active = true;
}

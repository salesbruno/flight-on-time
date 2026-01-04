package com.flightontime.repository;

import com.flightontime.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, String> {
}
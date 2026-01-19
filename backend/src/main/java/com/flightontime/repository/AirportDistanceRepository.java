package com.flightontime.repository;

import com.flightontime.model.AirportDistance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportDistanceRepository extends JpaRepository<AirportDistance, Long> {

    Optional<AirportDistance> findByIataCodeOriginAndIataCodeDest(String iataCodeOrigin, String iataCodeDest);
}
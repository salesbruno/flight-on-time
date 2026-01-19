package com.flightontime.dto;

public record AirportDistanceResponse(
        String iataCodeOrigin,
        String iataCodeDest,
        Integer distance
) {}
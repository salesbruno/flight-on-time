package com.flightontime.dto;

public record AirportResponse(
        String iataCode,
        String airportName,
        String cityName,
        String stateName
) {}
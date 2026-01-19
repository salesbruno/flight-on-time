package com.flightontime.controller;

import com.flightontime.dto.AirportDistanceResponse;
import com.flightontime.service.AirportDistanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AirportDistanceController {

    private final AirportDistanceService service;

    public AirportDistanceController(AirportDistanceService service) {
        this.service = service;
    }

    @GetMapping("/airports/distance")
    public Optional<AirportDistanceResponse> getDistance(
            @RequestParam String origem,
            @RequestParam String destino) {
        return service.buscarDistancia(origem, destino);
    }
}
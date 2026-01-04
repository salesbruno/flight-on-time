package com.flightontime.controller;

import com.flightontime.dto.AirportResponse;
import com.flightontime.service.AirportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirportController {

    private final AirportService service;

    public AirportController(AirportService service) {
        this.service = service;
    }

    @GetMapping("/airports")
    public List<AirportResponse> listarTodos() {
        return service.listarTodos();
    }
}

package com.flightontime.controller;

import com.flightontime.dto.CompanyResponse;
import com.flightontime.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping("/companies")
    public List<CompanyResponse> listarTodas() {
        return service.listarTodas();
    }
}
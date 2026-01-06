package com.flightontime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flightontime.dto.VooRequest;
import com.flightontime.dto.VooResponse;
import com.flightontime.service.VooService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/predict")
public class VooController {

    private final VooService vooService;

    public VooController(VooService vooService) {
        this.vooService = vooService;
    }

    @PostMapping
    public ResponseEntity<VooResponse> predict(@Valid @RequestBody VooRequest request) throws JsonProcessingException {
        VooResponse resposta = vooService.predict(request);
        return ResponseEntity.ok(resposta);
    }
}
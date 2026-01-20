package com.flightontime.controller;

import com.flightontime.dto.DelayStatsResponse;
import com.flightontime.dto.DelayStatsState;
import com.flightontime.service.DelayStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stats")

public class DelayStatsController {

   @Autowired
    DelayStatsService service;

    @GetMapping
    public DelayStatsResponse calcularEstatisticas(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return service.calcularEstatisticasPeriodo(start, end);
    }

    @GetMapping("/estado")
    public List<DelayStatsState> getStatsPorEstado(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return service.calcularPorEstado(start, end);
    }
}
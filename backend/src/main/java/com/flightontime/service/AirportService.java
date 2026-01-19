package com.flightontime.service;

import com.flightontime.model.Airport;
import com.flightontime.dto.AirportResponse;
import com.flightontime.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AirportService {

    private final AirportRepository repository;

    public AirportService(AirportRepository repository) {
        this.repository = repository;
    }

    public List<AirportResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Airport::getAirportName))
                .map(a -> new AirportResponse(
                        a.getIataCode(),
                        a.getAirportName(),
                        a.getCityName(),
                        a.getStateName()
                ))
                .toList();
    }
}

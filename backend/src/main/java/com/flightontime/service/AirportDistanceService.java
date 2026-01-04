package com.flightontime.service;

import com.flightontime.dto.AirportDistanceResponse;
import com.flightontime.model.Airport;
import com.flightontime.model.AirportDistance;
import com.flightontime.repository.AirportDistanceRepository;
import com.flightontime.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportDistanceService {

    private final AirportDistanceRepository distanceRepository;
    private final AirportRepository airportRepository;

    public AirportDistanceService(AirportDistanceRepository distanceRepository,
                                  AirportRepository airportRepository) {
        this.distanceRepository = distanceRepository;
        this.airportRepository = airportRepository;
    }

    public Optional<AirportDistanceResponse> buscarDistancia(String origem, String destino) {
        // 1. tenta buscar na tabela airports_distance
        Optional<AirportDistance> existente =
                distanceRepository.findByIataCodeOriginAndIataCodeDest(origem, destino);

        if (existente.isPresent()) {
            AirportDistance d = existente.get();
            return Optional.of(new AirportDistanceResponse(
                    d.getIataCodeOrigin(),
                    d.getIataCodeDest(),
                    d.getDistance()
            ));
        }

        // 2. se não existir, calcula via fórmula Haversine
        Optional<Airport> a1 = airportRepository.findById(origem);
        Optional<Airport> a2 = airportRepository.findById(destino);

        if (a1.isPresent() && a2.isPresent()) {
            double lat1 = Math.toRadians(a1.get().getLatitude().doubleValue());
            double lon1 = Math.toRadians(a1.get().getLongitude().doubleValue());
            double lat2 = Math.toRadians(a2.get().getLatitude().doubleValue());
            double lon2 = Math.toRadians(a2.get().getLongitude().doubleValue());

            // fórmula Haversine (raio da Terra = 6371 km)
            double distancia = 6371 * Math.acos(
                    Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)
                            + Math.sin(lat1) * Math.sin(lat2)
            );

            int distanciaKm = (int) Math.round(distancia);

            return Optional.of(new AirportDistanceResponse(origem, destino, distanciaKm));
        }

        // 3. se não encontrar os aeroportos
        return Optional.empty();
    }
}
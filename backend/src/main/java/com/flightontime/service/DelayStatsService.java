package com.flightontime.service;

import com.flightontime.dto.DelayStatsResponse;
import com.flightontime.dto.DelayStatsState;
import com.flightontime.model.DelayStats;
import com.flightontime.repository.DelayStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DelayStatsService {

    @Autowired
    private DelayStatsRepository repository;

    @Autowired
    private WebClient.Builder webClient;

    @Value("${fastapi.url}")
    private String fastApiUrl;

    public DelayStatsResponse calcularEstatisticasPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<DelayStats> voos = repository.findByDataPartidaBetween(inicio, fim);

        List<Map<String, Object>> resultados = new ArrayList<>();
        List<Double> probabilidades = new ArrayList<>();

        for (DelayStats voo : voos) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("companhia", voo.getCompanhia());
            payload.put("origem", voo.getOrigem());
            payload.put("destino", voo.getDestino());
            payload.put("estado_origem", voo.getEstadoOrigem());
            payload.put("estado_destino", voo.getEstadoDestino());
            payload.put("distancia_km", voo.getDistanciaKm());
            payload.put("data_partida", voo.getDataPartida().toString());

            Double probabilidade = webClient.build()
                    .post()
                    .uri(fastApiUrl)
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .onErrorReturn(0.0)
                    .block();

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("voo", payload);
            resultado.put("probabilidade", probabilidade);
            resultados.add(resultado);

            probabilidades.add(probabilidade);
        }

        // cálculos estatísticos
        double media = probabilidades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double min = probabilidades.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        double max = probabilidades.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

        double variancia = probabilidades.stream()
                .mapToDouble(p -> Math.pow(p - media, 2))
                .sum() / (probabilidades.isEmpty() ? 1 : probabilidades.size());
        double desvio = Math.sqrt(variancia);

        return DelayStatsResponse.builder()
                .start(inicio)
                .end(fim)
                .totalVoos(voos.size())
                .resultados(resultados)
                .media(media)
                .min(min)
                .max(max)
                .desvio(desvio)
                .build();
    }

    public List<DelayStatsState> calcularPorEstado(LocalDateTime inicio, LocalDateTime fim) {
        List<DelayStats> voos = repository.findByDataPartidaBetween(inicio, fim);

        Map<String, List<Double>> grupos = new HashMap<>();

        for (DelayStats voo : voos) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("companhia", voo.getCompanhia());
            payload.put("origem", voo.getOrigem());
            payload.put("destino", voo.getDestino());
            payload.put("estado_origem", voo.getEstadoOrigem());
            payload.put("estado_destino", voo.getEstadoDestino());
            payload.put("distancia_km", voo.getDistanciaKm());
            payload.put("data_partida", voo.getDataPartida().toString());


            Double probabilidade = webClient.build()
                    .post()
                    .uri(fastApiUrl)
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .onErrorReturn(0.0)
                    .block();

            grupos.computeIfAbsent(voo.getEstadoOrigem(), k -> new ArrayList<>()).add(probabilidade);
        }

        List<DelayStatsState> estatisticas = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : grupos.entrySet()) {
            List<Double> probs = entry.getValue();
            double media = probs.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double min = probs.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            double max = probs.stream().mapToDouble(Double::doubleValue).max().orElse(0);
            double variancia = probs.stream().mapToDouble(p -> Math.pow(p - media, 2)).sum() / probs.size();
            double desvio = Math.sqrt(variancia);

            DelayStatsState stats = new DelayStatsState(entry.getKey(), probs.size(), media, min, max, desvio);
            estatisticas.add(stats);
        }

        return estatisticas;
    }
}
package com.flightontime.service;

import com.flightontime.dto.VooRequest;
import com.flightontime.dto.VooResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class VooService {

    private final WebClient.Builder webClient;

    @Value("${fastapi.url}")
    private String fastApiUrl;

    @Value("${prediction.threshold}")
    private double threshold;


    public VooService(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    //Envia para Modelo
    public VooResponse predict(VooRequest request) {

        Double probability = webClient.build()
                .post()
                .uri(fastApiUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Double.class)
                .onErrorReturn(0.0)
                .block();

        String prediction = probability >= threshold ? "Atrasado" : "Pontual";

        return new VooResponse(prediction, probability);
    }
}
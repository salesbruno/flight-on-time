package com.flightontime.service;

import com.flightontime.dto.CompanyResponse;
import com.flightontime.model.Company;
import com.flightontime.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<CompanyResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Company::getName))
                .map(c -> new CompanyResponse(
                        c.getIataCode(),
                        c.getName()
                ))
                .toList();
    }
}
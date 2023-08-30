package com.example.jpaonetomany.service;

import com.example.jpaonetomany.Model.Kommune;
import com.example.jpaonetomany.repositories.KommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceGetKommunerImpl implements ApiServiceGetKommuner {

    private RestTemplate restTemplate;

    public ApiServiceGetKommunerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String kommuneUrl = "https://api.dataforsyningen.dk/kommuner";

    @Autowired
    KommuneRepository kommuneRepository;

    private void saveKommuner(List<Kommune> kommuner) {
        kommuner.forEach(kom -> kommuneRepository.save(kom));
    }

    @Override
    public List<Kommune> getKommuner() {
        List<Kommune> ListAfKommuner = new ArrayList<>();
        ResponseEntity<List<Kommune>> kommuneResponse =
                restTemplate.exchange(kommuneUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Kommune>>() {
                });
        List<Kommune> kommuner = kommuneResponse.getBody();
        saveKommuner(kommuner);
        return kommuner;
    }



}

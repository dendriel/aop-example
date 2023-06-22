package com.rozsa.aopexample.services;

import com.rozsa.aopexample.annotations.Stopwatch;
import com.rozsa.aopexample.clients.CatsClient;
import com.rozsa.aopexample.clients.response.CatFactsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CatsService {

    private final CatsClient catsClient;

    @Stopwatch
    public List<String> getFacts(final String filter) {
        List<CatFactsResponse> catsFacts = catsClient.getCatsFacts();

        List<String> facts = catsFacts.stream()
                .map(CatFactsResponse::getText)
                .filter(fact -> fact.contains(filter))
                .toList();

        return facts;
    }
}

/**
 * logging
 * temporizador
 * retry
 * caching
 * data collection
 * fallback
 * circuit breaker
 * etc.
 */









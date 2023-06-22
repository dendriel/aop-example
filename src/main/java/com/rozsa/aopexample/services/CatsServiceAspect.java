package com.rozsa.aopexample.services;

import com.rozsa.aopexample.annotations.CollectParameters;
import com.rozsa.aopexample.annotations.LogParameters;
import com.rozsa.aopexample.annotations.Stopwatch;
import com.rozsa.aopexample.clients.CatsClient;
import com.rozsa.aopexample.clients.response.CatFactsResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CatsServiceAspect {

    private final CatsClient catsClient;

    @LogParameters
    @Stopwatch
    @Retry(name = "cats-client")
    @Cacheable("catsfacts")
    @CollectParameters
    @CircuitBreaker(name = "cats-client", fallbackMethod = "getFactsFallback")
    public List<String> getFacts(String filter) {

        List<CatFactsResponse> catsFacts = catsClient.getCatsFacts();

        List<String> facts = catsFacts.stream()
                .map(CatFactsResponse::getText)
                .filter(fact -> fact.contains(filter))
                .toList();

        return facts;
    }

    private List<String> getFactsFallback() {
        return List.of("Random cat fact as fallback");
    }
}

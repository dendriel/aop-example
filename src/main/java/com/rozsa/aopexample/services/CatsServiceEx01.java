package com.rozsa.aopexample.services;

import com.rozsa.aopexample.clients.CatsClient;
import com.rozsa.aopexample.clients.response.CatFactsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Adição de log
 */
@Service
@RequiredArgsConstructor
public class CatsServiceEx01 {
    public static final Logger logger = getLogger(CatsServiceEx01.class); /** nova dependencia */

    private final CatsClient catsClient;

    public List<String> getFacts(String filter) {
        logger.info("getFacts with filter: {}", filter); /** nova linha */

        List<CatFactsResponse> catsFacts = catsClient.getCatsFacts();

        List<String> facts = catsFacts.stream()
                .map(CatFactsResponse::getText)
                .filter(fact -> fact.contains(filter))
                .toList();

        return facts;
    }
}

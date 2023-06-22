package com.rozsa.aopexample.services;

import com.rozsa.aopexample.clients.CatsClient;
import com.rozsa.aopexample.clients.response.CatFactsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Adição de temporizador
 */
@Service
@RequiredArgsConstructor
public class CatsServiceEx02 {
    public static final Logger logger = getLogger(CatsServiceEx02.class);

    private final CatsClient catsClient;

    public List<String> getFacts(String filter) {
        StopWatch sw = new StopWatch(); /** nova linha */
        sw.start(); /** nova linha */

        logger.info("getFacts with filter: {}", filter);

        List<CatFactsResponse> catsFacts = catsClient.getCatsFacts();

        List<String> facts = catsFacts.stream()
                .map(CatFactsResponse::getText)
                .filter(fact -> fact.contains(filter))
                .toList();

        sw.stop();  /** nova linha */
        logger.info("getFacts - call to get cats facts took {} ms", sw.getTotalTimeMillis());  /** nova linha */

        return facts;
    }
}

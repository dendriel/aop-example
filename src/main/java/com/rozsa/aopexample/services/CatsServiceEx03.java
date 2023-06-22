package com.rozsa.aopexample.services;

import com.rozsa.aopexample.clients.CatsClient;
import com.rozsa.aopexample.clients.response.CatFactsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Adição de retentativas
 */
@Service
@RequiredArgsConstructor
public class CatsServiceEx03 {
    public static final Logger logger = getLogger(CatsServiceEx03.class);

    private final CatsClient catsClient;

    @Value ("${cats-client.retries}")
    Integer maxRetries;

    public List<String> getFacts(String filter) {
        StopWatch sw = new StopWatch();
        sw.start();

        logger.info("getFacts with filter: {}", filter);

        Integer retries = 0;
        List<CatFactsResponse> catsFacts = null;

        do {
            try {
                catsFacts = catsClient.getCatsFacts();
            } catch (Exception e) {
                logger.debug("Failed to getFacts from cats. Attempt #{} - Error: {}", retries, e.getMessage());
            }
        } while (catsFacts == null && retries++ < maxRetries);

        /**
         * Aqui as diferentes responsabilidades começam a se cruzar.
         */
        if (catsFacts == null) {
            sw.stop();
            logger.info("getFacts - failed to get cats facts! Time spent: {}", sw.getTotalTimeMillis());
            return List.of();
        }

        List<String> facts = catsFacts.stream()
                .map(CatFactsResponse::getText)
                .filter(fact -> fact.contains(filter))
                .toList();

        sw.stop();
        logger.info("getFacts - call to get cats facts took {} ms", sw.getTotalTimeMillis());

        return facts;
    }
}

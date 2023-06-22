package com.rozsa.aopexample.services;

import com.rozsa.aopexample.clients.CatsClient;
import com.rozsa.aopexample.clients.response.CatFactsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Adição de caching
 */
@Service
@RequiredArgsConstructor
public class CatsServiceEx04 {
    public static final Logger logger = getLogger(CatsServiceEx04.class);

    private final CatsClient catsClient;

    @Value ("${cats-client.retries}")
    Integer maxRetries;

    Map<String, List<String>> localCache = new HashMap<>();

    /** novo método */
    private Optional<List<String>> getCacheByKey(String key) {
        return Optional
                .ofNullable(localCache.getOrDefault(key, null));
    }

    /** novo método */
    private void putCache(String key, List<String> value) {
        localCache.put(key, value);
    }

    /** novo método */
    private void invalidatedOldCachedEntries() {
        // lógica/regras de invalidação de cache
    }


    public List<String> getFacts(String filter) {
        StopWatch sw = new StopWatch();
        sw.start();

        invalidatedOldCachedEntries();

        Optional<List<String>> cachedValue = getCacheByKey(filter);
        if (cachedValue.isPresent()) {
            sw.stop();
            logger.info("getFacts - facts retrieved from cache! Time spent: {}", sw.getTotalTimeMillis());
            return cachedValue.get();
        }

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

        if (catsFacts == null) {
            putCache(filter, List.of());

            sw.stop();
            logger.info("getFacts - failed to get cats facts! Time spent: {}", sw.getTotalTimeMillis());
            return List.of();
        }

        List<String> facts = catsFacts.stream()
                .map(CatFactsResponse::getText)
                .filter(fact -> fact.contains(filter))
                .toList();


        putCache(filter, List.of());

        sw.stop();
        logger.info("getFacts - call to get cats facts took {} ms", sw.getTotalTimeMillis());

        return facts;
    }
}

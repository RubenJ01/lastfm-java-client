package io.github.rubeneekhof.lastfm.domain.port;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import io.github.rubeneekhof.lastfm.infrastructure.http.RetryExecutor;
import io.github.rubeneekhof.lastfm.infrastructure.http.RetryPolicy;

import java.io.IOException;
import java.util.Map;

public interface LastFmApiGateway {
    default <T> T getAndParse(HttpExecutor http, ObjectMapper mapper, String method,
                              Map<String, String> params, Class<T> clazz) {
        try {
            // Wrap HTTP call with retry logic for transient failures
            String body = RetryExecutor.execute(RetryPolicy.lastFmDefaults(), () -> {
                try {
                    return http.get(method, params);
                } catch (IOException e) {
                    throw new LastFmException(0, "HTTP request failed: " + e.getMessage(), e);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new LastFmException(0, "HTTP request interrupted", e);
                }
            });
            
            return mapper.readValue(body, clazz);
        } catch (LastFmException e) {
            throw e; // Re-throw LastFmException (may have been thrown by HttpExecutor or RetryExecutor)
        } catch (JsonProcessingException e) {
            throw new LastFmException(0, "Failed to parse API response for method: " + method, e);
        }
    }
}

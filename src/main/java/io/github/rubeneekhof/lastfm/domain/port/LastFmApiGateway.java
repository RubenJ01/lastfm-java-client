package io.github.rubeneekhof.lastfm.domain.port;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

import java.util.Map;

public interface LastFmApiGateway {
    default <T> T getAndParse(HttpExecutor http, ObjectMapper mapper, String method,
                              Map<String,String> params, Class<T> clazz) {
        try {
            String body = http.get(method, params);
            return mapper.readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute API call: " + method, e);
        }
    }
}

package io.github.rubeneekhof.lastfm.infrastructure.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.exception.LastFmException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpExecutor {

    private static final String BASE_URL = "https://ws.audioscrobbler.com/2.0/";
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpExecutor(String apiKey, ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    public String get(String method, Map<String, String> params) throws IOException, InterruptedException {
        String query = params.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String url = BASE_URL + "?method=" + method + "&api_key=" + apiKey + "&format=json&" + query;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        
        // Check for Last.fm API errors (can be returned with HTTP 200 or other status codes)
        try {
            JsonNode rootNode = objectMapper.readTree(body);
            if (rootNode.has("error") && !rootNode.get("error").isNull()) {
                int errorCode = rootNode.get("error").asInt();
                if (errorCode != 0) {
                    String message = rootNode.has("message") 
                            ? rootNode.get("message").asText() 
                            : "Unknown error";
                    throw new LastFmException(errorCode, message);
                }
            }
        } catch (LastFmException e) {
            throw e; // Re-throw Last.fm API errors
        } catch (Exception e) {
            // If JSON parsing fails, check HTTP status code for common Last.fm errors
            if (response.statusCode() == 403) {
                // HTTP 403 typically means invalid API key (error 10)
                throw new LastFmException(10, "Invalid API key - You must be granted a valid key by last.fm");
            }
            // If JSON parsing fails and not a known status code, continue to generic error handling
        }

        // Only throw IOException if it's not a Last.fm API error (which was already thrown above)
        if (response.statusCode() != 200) {
            throw new IOException("HTTP error: " + response.statusCode());
        }

        return body;
    }
}

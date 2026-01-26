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

  private static final String DEFAULT_BASE_URL = "https://ws.audioscrobbler.com/2.0/";
  private final String apiKey;
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;
  private final String baseUrl;
  private final String userAgent;

  public HttpExecutor(String apiKey, ObjectMapper objectMapper) {
    this(apiKey, objectMapper, DEFAULT_BASE_URL, null, null);
  }

  public HttpExecutor(
      String apiKey, ObjectMapper objectMapper, String baseUrl, HttpClient httpClient,
      String userAgent) {
    this.apiKey = apiKey;
    this.objectMapper = objectMapper;
    this.baseUrl = baseUrl != null ? baseUrl : DEFAULT_BASE_URL;
    this.httpClient = httpClient != null ? httpClient : HttpClient.newHttpClient();
    this.userAgent = userAgent;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String get(String method, Map<String, String> params)
      throws IOException, InterruptedException {
    String query =
        params.entrySet().stream()
            .map(
                e ->
                    URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)
                        + "="
                        + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

    String url = baseUrl + "?method=" + method + "&api_key=" + apiKey + "&format=json&" + query;

    HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
    if (userAgent != null) {
      requestBuilder.header("User-Agent", userAgent);
    }
    HttpRequest request = requestBuilder.GET().build();

    return executeRequest(request);
  }

  public String post(String method, Map<String, String> params)
      throws IOException, InterruptedException {
    Map<String, String> allParams = new java.util.HashMap<>(params);
    allParams.put("method", method);
    allParams.put("api_key", apiKey);
    allParams.put("format", "json");

    String formData =
        allParams.entrySet().stream()
            .map(
                e ->
                    URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)
                        + "="
                        + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

    HttpRequest.Builder requestBuilder =
        HttpRequest.newBuilder()
            .uri(URI.create(baseUrl))
            .header("Content-Type", "application/x-www-form-urlencoded");
    if (userAgent != null) {
      requestBuilder.header("User-Agent", userAgent);
    }
    HttpRequest request =
        requestBuilder
            .POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8))
            .build();

    return executeRequest(request);
  }

  private String executeRequest(HttpRequest request) throws IOException, InterruptedException {
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    String body = response.body();

      System.out.println(body);

    try {
      JsonNode rootNode = objectMapper.readTree(body);
      if (rootNode.has("error") && !rootNode.get("error").isNull()) {
        int errorCode = rootNode.get("error").asInt();
        if (errorCode != 0) {
          String message =
              rootNode.has("message") ? rootNode.get("message").asText() : "Unknown error";
          throw new LastFmException(errorCode, message);
        }
      }
    } catch (LastFmException e) {
      throw e;
    } catch (Exception e) {
      if (response.statusCode() == 403) {
        throw new LastFmException(
            10, "Invalid API key - You must be granted a valid key by last.fm");
      }
    }

    if (response.statusCode() != 200) {
      throw new IOException("HTTP error: " + response.statusCode());
    }

    return body;
  }
}

package io.github.rubeneekhof.lastfm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

/**
 * Immutable configuration for LastFmClient. Contains all configurable aspects of the client
 * including HTTP settings, JSON parsing, and API credentials.
 */
public record ClientConfig(
        String apiKey,
    String apiSecret,
    String sessionKey,
    String baseUrl,
    HttpClient httpClient,
    ObjectMapper objectMapper,
    String userAgent) {

  private static final String DEFAULT_BASE_URL = "https://ws.audioscrobbler.com/2.0/";
  private static final String DEFAULT_USER_AGENT = "lastfm-java-client/1.0.0";

  /**
   * Creates a default configuration with the provided API key. Uses default HttpClient,
   * ObjectMapper, base URL, and user agent.
   *
   * @param apiKey the Last.fm API key
   * @return a ClientConfig with defaults
   */
  public static ClientConfig withApiKey(String apiKey) {
    return new ClientConfig(
        apiKey,
        null,
        null,
        DEFAULT_BASE_URL,
        HttpClient.newHttpClient(),
        new ObjectMapper(),
        DEFAULT_USER_AGENT);
  }

  /**
   * Creates a configuration with API key and secret for authenticated operations.
   *
   * @param apiKey the Last.fm API key
   * @param apiSecret the Last.fm API secret
   * @return a ClientConfig with defaults
   */
  public static ClientConfig withApiKeyAndSecret(String apiKey, String apiSecret) {
    return new ClientConfig(
        apiKey,
        apiSecret,
        null,
        DEFAULT_BASE_URL,
        HttpClient.newHttpClient(),
        new ObjectMapper(),
        DEFAULT_USER_AGENT);
  }

  /**
   * Creates a configuration for authenticated operations with session key.
   *
   * @param apiKey the Last.fm API key
   * @param apiSecret the Last.fm API secret
   * @param sessionKey the authenticated session key
   * @return a ClientConfig with defaults
   */
  public static ClientConfig authenticated(
      String apiKey, String apiSecret, String sessionKey) {
    return new ClientConfig(
        apiKey,
        apiSecret,
        sessionKey,
        DEFAULT_BASE_URL,
        HttpClient.newHttpClient(),
        new ObjectMapper(),
        DEFAULT_USER_AGENT);
  }
}

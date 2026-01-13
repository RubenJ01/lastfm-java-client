package io.github.rubeneekhof.lastfm.application.auth;

import io.github.rubeneekhof.lastfm.domain.model.Session;
import io.github.rubeneekhof.lastfm.domain.port.AuthGateway;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthService {

  private static final String AUTH_BASE_URL = "http://www.last.fm/api/auth/";
  private final AuthGateway gateway;

  public AuthService(AuthGateway gateway) {
    this.gateway = gateway;
  }

  public String getToken() {
    return gateway.getToken();
  }

  public String getAuthorizationUrl(String apiKey, String token) {
    if (apiKey == null || apiKey.isBlank()) {
      throw new IllegalArgumentException("API key must not be blank");
    }
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("Token must not be blank");
    }
    return AUTH_BASE_URL
        + "?api_key="
        + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
        + "&token="
        + URLEncoder.encode(token, StandardCharsets.UTF_8);
  }

  public Session getSession(String token) {
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("Token must not be blank");
    }
    return gateway.getSession(token);
  }
}

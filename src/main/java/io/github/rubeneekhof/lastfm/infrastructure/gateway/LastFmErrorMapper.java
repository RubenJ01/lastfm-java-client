package io.github.rubeneekhof.lastfm.infrastructure.gateway;

import io.github.rubeneekhof.lastfm.domain.model.error.*;

/**
 * Maps Last.fm API error codes to domain-level failures. Prevents numeric error codes from leaking
 * to domain/application layers.
 */
public class LastFmErrorMapper {

  public static LastFmFailure map(int code, String message) {
    return switch (code) {
      case 4, 9, 10, 26 -> new Unauthorized();
      case 6, 7, 13 -> new InvalidRequest(message);
      case 2, 3, 5 -> new InvalidRequest("API misconfiguration");
      case 11, 16 -> new ServiceUnavailable();
      case 29 -> new RateLimited();
      default -> new UnknownFailure(message != null ? message : "Unknown error");
    };
  }
}

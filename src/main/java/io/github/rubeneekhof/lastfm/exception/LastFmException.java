package io.github.rubeneekhof.lastfm.exception;

/**
 * Infrastructure-level exception for Last.fm API errors. Contains the numeric error code returned
 * by the API. This should not leak to domain/application layers - use LastFmFailure instead.
 */
public class LastFmException extends RuntimeException {
  private final int code;

  public LastFmException(int code, String message) {
    super(message);
                 this.code = code;
  }

  public LastFmException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public int code() {
    return code;
  }
}

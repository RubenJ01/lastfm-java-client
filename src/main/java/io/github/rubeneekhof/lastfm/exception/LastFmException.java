package io.github.rubeneekhof.lastfm.exception;

public class LastFmException extends RuntimeException {
    public LastFmException(String message) {
        super(message);
    }

    public LastFmException(String message, Throwable cause) {
        super(message, cause);
    }
}
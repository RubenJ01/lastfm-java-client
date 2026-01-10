package io.github.rubeneekhof.lastfm.domain.model.error;

/**
 * Domain-level exception that wraps a LastFmFailure.
 * This prevents Last.fm error codes from leaking to application/domain layers.
 */
public class LastFmFailureException extends RuntimeException {
    private final LastFmFailure failure;

    public LastFmFailureException(LastFmFailure failure) {
        super(failure.toString());
        this.failure = failure;
    }

    public LastFmFailure failure() {
        return failure;
    }
}

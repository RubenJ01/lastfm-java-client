package io.github.rubeneekhof.lastfm.domain.model.error;

/**
 * Domain-level representation of Last.fm API failures.
 * Prevents numeric error codes from leaking to domain/application layers.
 */
public sealed interface LastFmFailure
        permits RateLimited, Unauthorized, NotFound, InvalidRequest, ServiceUnavailable, UnknownFailure {
}

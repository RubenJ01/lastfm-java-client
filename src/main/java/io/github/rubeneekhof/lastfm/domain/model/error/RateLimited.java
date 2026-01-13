package io.github.rubeneekhof.lastfm.domain.model.error;

public record RateLimited(String reason) implements LastFmFailure {}

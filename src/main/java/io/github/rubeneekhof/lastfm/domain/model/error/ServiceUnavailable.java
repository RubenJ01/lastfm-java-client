package io.github.rubeneekhof.lastfm.domain.model.error;

public record ServiceUnavailable(String reason) implements LastFmFailure {}

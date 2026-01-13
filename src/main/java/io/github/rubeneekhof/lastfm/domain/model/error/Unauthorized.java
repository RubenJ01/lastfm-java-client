package io.github.rubeneekhof.lastfm.domain.model.error;

public record Unauthorized(String reason) implements LastFmFailure {}

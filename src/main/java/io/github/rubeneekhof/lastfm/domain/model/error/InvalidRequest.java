package io.github.rubeneekhof.lastfm.domain.model.error;

public record InvalidRequest(String reason) implements LastFmFailure {}

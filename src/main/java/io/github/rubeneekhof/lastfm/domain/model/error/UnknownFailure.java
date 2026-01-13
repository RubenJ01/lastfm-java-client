package io.github.rubeneekhof.lastfm.domain.model.error;

public record UnknownFailure(String message) implements LastFmFailure {}

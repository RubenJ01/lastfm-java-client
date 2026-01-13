package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record ScrobbleResponse(int accepted, int ignored, List<ScrobbleResult> results) {}

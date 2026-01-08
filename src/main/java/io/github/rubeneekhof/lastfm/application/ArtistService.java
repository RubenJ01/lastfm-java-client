package io.github.rubeneekhof.lastfm.application;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;

import java.util.List;

public class ArtistService {

    private static final boolean DEFAULT_AUTOCORRECT = true;
    private static final int DEFAULT_LIMIT = 5;

    private final ArtistGateway gateway;

    public ArtistService(ArtistGateway gateway) {
        this.gateway = gateway;
    }

    public Artist getInfo(String artistName) {
        validateArtistName(artistName);
        return gateway.getInfo(artistName);
    }

    public List<Artist> getSimilar(String artistName) {
        return getSimilar(artistName, DEFAULT_AUTOCORRECT, DEFAULT_LIMIT);
    }

    public List<Artist> getSimilar(String artistName, boolean autocorrect, int limit) {
        validateArtistName(artistName);
        validateLimit(limit);
        return gateway.getSimilar(artistName, autocorrect, limit);
    }

    private void validateArtistName(String artistName) {
        if (artistName == null || artistName.isBlank()) {
            throw new IllegalArgumentException("Artist name must not be blank");
        }
    }

    private void validateLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than zero");
        }
    }
}

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
        return getInfo(ArtistGetInfoRequest.artist(artistName).build());
    }

    public Artist getInfo(String artist, String lang) {
        validateArtistName(artist);
        return getInfo(ArtistGetInfoRequest.artist(artist).lang(lang).build());
    }

    public Artist getInfo(String artist, boolean autocorrect) {
        validateArtistName(artist);
        return getInfo(ArtistGetInfoRequest.artist(artist).autocorrect(autocorrect).build());
    }

    public Artist getInfo(ArtistGetInfoRequest request) {
        return gateway.getInfo(
                request.artist(),
                request.mbid(),
                request.lang(),
                request.autocorrect(),
                request.username()
        );
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

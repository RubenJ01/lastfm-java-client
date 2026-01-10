package io.github.rubeneekhof.lastfm.application;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;

import java.util.List;
import java.util.Optional;

public class ArtistService {

    private static final boolean DEFAULT_AUTOCORRECT = true;
    private static final int DEFAULT_LIMIT = 5;

    private final ArtistGateway gateway;

    public ArtistService(ArtistGateway gateway) {
        this.gateway = gateway;
    }

    public Artist getInfo(String artist) {
        validateArtistName(artist);
        return getInfo(ArtistGetInfoRequest.artist(artist).build());
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
        validateArtistName(artistName);
        return getSimilar(ArtistGetSimilarRequest.artist(artistName)
                .autocorrect(DEFAULT_AUTOCORRECT)
                .limit(DEFAULT_LIMIT)
                .build());
    }

    public List<Artist> getSimilar(String artistName, int limit) {
        validateArtistName(artistName);
        validateLimit(limit);
        return getSimilar(ArtistGetSimilarRequest.artist(artistName)
                .autocorrect(DEFAULT_AUTOCORRECT)
                .limit(limit)
                .build());
    }

    public List<Artist> getSimilar(String artistName, boolean autocorrect, int limit) {
        validateArtistName(artistName);
        validateLimit(limit);
        return getSimilar(ArtistGetSimilarRequest.artist(artistName)
                .autocorrect(autocorrect)
                .limit(limit)
                .build());
    }

    public List<Artist> getSimilar(ArtistGetSimilarRequest request) {
        return gateway.getSimilar(
                request.artist(),
                request.mbid(),
                request.autocorrect(),
                request.limit()
        );
    }

    public Optional<Artist> getCorrection(String artist) {
        validateArtistName(artist);
        return gateway.getCorrection(artist);
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

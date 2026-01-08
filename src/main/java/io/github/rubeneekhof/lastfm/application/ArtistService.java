package io.github.rubeneekhof.lastfm.application;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;

import java.util.List;

public class ArtistService {

    private final ArtistGateway gateway;

    public ArtistService(ArtistGateway gateway) {
        this.gateway = gateway;
    }

    public Artist getInfo(String artist) {
        if (artist == null || artist.isBlank()) {
            throw new IllegalArgumentException("Artist name must not be blank");
        }
        return gateway.getInfo(artist);
    }

    public List<Artist> getSimilar(String artistName, int limit) {
        if (artistName == null || artistName.isBlank()) {
            throw new IllegalArgumentException("Artist name must not be blank");
        }
        return gateway.getSimilar(artistName, limit);
    }

    public List<Artist> getSimilar(String artistName) {
        return getSimilar(artistName, 5);
    }

}
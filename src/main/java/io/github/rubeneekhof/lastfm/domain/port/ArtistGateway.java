package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistGateway extends LastFmApiGateway {
    Artist getInfo(String artist, String mbid, String lang, Boolean autocorrect, String username);
    List<Artist> getSimilar(String artist, String mbid, Boolean autocorrect, Integer limit);
    Optional<Artist> getCorrection(String artist);
}

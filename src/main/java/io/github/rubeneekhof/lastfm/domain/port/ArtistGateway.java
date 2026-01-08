package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Artist;

import java.util.List;

public interface ArtistGateway extends LastFmApiGateway {
    Artist getInfo(String artist);
    List<Artist> getSimilar(String artist, boolean autocorrect,int limit);
}

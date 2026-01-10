package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.LibraryArtist;

import java.util.List;

public interface LibraryGateway extends LastFmApiGateway {
    List<LibraryArtist> getArtists(String user, Integer page, Integer limit);
}

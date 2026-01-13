package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Album;

public interface AlbumGateway extends LastFmApiGateway {
  Album getInfo(
      String artist, String album, String mbid, Boolean autocorrect, String username, String lang);
}

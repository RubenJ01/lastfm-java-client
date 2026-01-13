package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import java.util.List;

public interface GeoGateway extends LastFmApiGateway {
  List<Artist> getTopArtists(String country, Integer page, Integer limit);
}

package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import java.util.List;

public interface ChartGateway extends LastFmApiGateway {
  List<Artist> getTopArtists(Integer page, Integer limit);
}

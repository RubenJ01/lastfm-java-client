package io.github.rubeneekhof.lastfm.infrastructure.gateway.geo;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.geo.response.GetTopArtistsResponse;
import java.util.List;

public class GeoMapper extends BaseMapper {

  public static List<Artist> from(GetTopArtistsResponse response) {
    if (response == null || response.topartists == null || response.topartists.artist == null) {
      return List.of();
    }

    return response.topartists.artist.stream().map(GeoMapper::from).toList();
  }

  private static Artist from(GetTopArtistsResponse.ArtistData data) {
    if (data == null) {
      return null;
    }

    return new Artist(
        data.name,
        data.mbid,
        data.url,
        mapImages(data.image, img -> new Artist.Image(img.getSize(), img.getUrl())),
        data.streamable,
        mapStats(data.listeners),
        List.of(),
        List.of(),
        null,
        null);
  }

  private static Artist.Stats mapStats(String listeners) {
    try {
      int listenersCount = parseNumber(listeners);
      return new Artist.Stats(listenersCount, 0, null);
    } catch (NumberFormatException e) {
      return new Artist.Stats(0, 0, null);
    }
  }
}

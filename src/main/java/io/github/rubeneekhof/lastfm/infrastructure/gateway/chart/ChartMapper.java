package io.github.rubeneekhof.lastfm.infrastructure.gateway.chart;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.response.GetTopArtistsResponse;
import java.util.List;

public class ChartMapper extends BaseMapper {

  public static List<Artist> from(GetTopArtistsResponse response) {
    if (response == null || response.artists == null || response.artists.artist == null) {
      return List.of();
    }

    return response.artists.artist.stream().map(ChartMapper::from).toList();
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
        mapStats(data.playcount, data.listeners),
        List.of(),
        List.of(),
        null,
        null);
  }

  private static Artist.Stats mapStats(String playcount, String listeners) {
    try {
      int plays = parseNumber(playcount);
      int listenersCount = parseNumber(listeners);
      return new Artist.Stats(listenersCount, plays, null);
    } catch (NumberFormatException e) {
      return new Artist.Stats(0, 0, null);
    }
  }
}

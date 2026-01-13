package io.github.rubeneekhof.lastfm.infrastructure.gateway.chart;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.response.GetTopArtistsResponse;
import java.util.List;
import java.util.Optional;

public class ChartMapper {

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
        mapImages(data.image),
        data.streamable,
        mapStats(data.playcount, data.listeners),
        List.of(),
        List.of(),
        null,
        null);
  }

  private static List<Artist.Image> mapImages(List<GetTopArtistsResponse.Image> images) {
    return Optional.ofNullable(images)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(img -> new Artist.Image(img.size, img.url)).toList())
        .orElse(List.of());
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

  private static int parseNumber(String value) {
    if (value == null || value.isBlank()) {
      return 0;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      try {
        return (int) Double.parseDouble(value);
      } catch (NumberFormatException ex) {
        return 0;
      }
    }
  }
}

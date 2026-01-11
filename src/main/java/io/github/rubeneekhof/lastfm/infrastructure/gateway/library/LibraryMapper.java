package io.github.rubeneekhof.lastfm.infrastructure.gateway.library;

import io.github.rubeneekhof.lastfm.domain.model.LibraryArtist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.library.response.GetArtistsResponse;
import java.util.List;
import java.util.Optional;

public class LibraryMapper {

  public static List<LibraryArtist> from(GetArtistsResponse response) {
    if (response == null || response.artists == null || response.artists.artist == null) {
      return List.of();
    }

    int page = parsePage(response.artists.attr);
    int perPage = parsePerPage(response.artists.attr);
    int baseRank = (page - 1) * perPage;

    List<GetArtistsResponse.ArtistData> artists = response.artists.artist;
    List<LibraryArtist> result = new java.util.ArrayList<>();
    for (int i = 0; i < artists.size(); i++) {
      result.add(from(artists.get(i), baseRank + i + 1));
    }
    return result;
  }

  private static LibraryArtist from(GetArtistsResponse.ArtistData data, int rank) {
    if (data == null) {
      return null;
    }

    return new LibraryArtist(
        data.name,
        data.mbid,
        data.url,
        mapImages(data.image),
        data.streamable,
        parseNumber(data.playcount),
        parseNumber(data.tagcount),
        rank);
  }

  private static int parsePage(GetArtistsResponse.Attr attr) {
    if (attr == null || attr.page == null) {
      return 1;
    }
    int parsed = parseNumber(attr.page);
    return parsed > 0 ? parsed : 1;
  }

  private static int parsePerPage(GetArtistsResponse.Attr attr) {
    if (attr == null || attr.perPage == null) {
      return 50;
    }
    int parsed = parseNumber(attr.perPage);
    return parsed > 0 ? parsed : 50;
  }

  private static List<LibraryArtist.Image> mapImages(List<GetArtistsResponse.Image> images) {
    return Optional.ofNullable(images)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(img -> new LibraryArtist.Image(img.size, img.url)).toList())
        .orElse(List.of());
  }

  private static int parseNumber(Object value) {
    if (value == null) {
      return 0;
    }

    if (value instanceof Number) {
      return ((Number) value).intValue();
    }

    if (value instanceof String) {
      String str = (String) value;
      if (str.isBlank()) {
        return 0;
      }
      try {
        return Integer.parseInt(str);
      } catch (NumberFormatException e) {
        try {
          return (int) Double.parseDouble(str);
        } catch (NumberFormatException ex) {
          return 0;
        }
      }
    }

    return 0;
  }
}

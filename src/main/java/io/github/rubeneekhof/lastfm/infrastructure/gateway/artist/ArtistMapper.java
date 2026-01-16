package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.model.artist.ArtistSearchResult;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.SearchResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import java.util.List;
import java.util.Optional;

public class ArtistMapper extends BaseMapper {

  public static Artist from(GetInfoResponse response) {
    if (response == null || response.artist == null) {
      return null;
    }

    GetInfoResponse.ArtistData data = response.artist;
    return new Artist(
        data.name,
        data.mbid,
        data.url,
        mapImages(data.image, img -> new Artist.Image(img.getSize(), img.getUrl())),
        data.streamable,
        mapStats(data.stats),
        mapSimilar(data.similar),
        mapTags(data.tags),
        mapBio(data.bio),
        null);
  }

  public static Artist from(GetSimilarResponse.SimilarArtistData data) {
    if (data == null) {
      return null;
    }

    return new Artist(
        data.name,
        data.mbid,
        data.url,
        mapImages(data.image, img -> new Artist.Image(img.getSize(), img.getUrl())),
        data.streamable,
        null,
        List.of(),
        List.of(),
        null,
        data.match);
  }

  public static List<Artist> from(GetSimilarResponse response) {
    if (response == null
        || response.similarartists == null
        || response.similarartists.artist == null) {
      return List.of();
    }

    return response.similarartists.artist.stream().map(ArtistMapper::from).toList();
  }

  public static Optional<Artist> from(GetCorrectionResponse response) {
    if (response == null
        || response.corrections == null
        || response.corrections.correction == null) {
      return Optional.empty();
    }

    GetCorrectionResponse.Correction correction = response.corrections.correction;
    if (correction.artist == null) {
      return Optional.empty();
    }

    return Optional.of(
        new Artist(
            correction.artist.name,
            correction.artist.mbid,
            correction.artist.url,
            List.of(),
            null,
            null,
            List.of(),
            List.of(),
            null,
            null));
  }

  private static Artist fromSimilarArtist(GetInfoResponse.ArtistData data) {
    if (data == null) {
      return null;
    }

    return new Artist(
        data.name, data.mbid, data.url, List.of(), null, null, List.of(), List.of(), null, null);
  }

  private static Artist.Stats mapStats(GetInfoResponse.Stats stats) {
    if (stats == null) {
      return null;
    }
    try {
      int listenersCount = parseNumber(stats.listeners);
      int plays = parseNumber(stats.plays);
      Integer userPlays = null;
      if (stats.userplaycount != null && !stats.userplaycount.isBlank()) {
        userPlays = parseNumber(stats.userplaycount);
      }
      return new Artist.Stats(listenersCount, plays, userPlays);
    } catch (NumberFormatException e) {
      return new Artist.Stats(0, 0, null);
    }
  }

  private static List<Artist> mapSimilar(GetInfoResponse.Similar similar) {
    return Optional.ofNullable(similar)
        .map(s -> s.artist)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(ArtistMapper::fromSimilarArtist).toList())
        .orElse(List.of());
  }

  private static List<Artist.Tag> mapTags(GetInfoResponse.Tags tags) {
    return Optional.ofNullable(tags)
        .map(t -> t.tag)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(t -> new Artist.Tag(t.name, t.url)).toList())
        .orElse(List.of());
  }

  private static Artist.Bio mapBio(GetInfoResponse.Bio bio) {
    return Optional.ofNullable(bio)
        .map(b -> new Artist.Bio(b.published, b.summary, b.content))
        .orElse(null);
  }

  public static ArtistSearchResult from(SearchResponse response) {
    if (response == null || response.results == null) {
      return new ArtistSearchResult(0, 0, 0, List.of());
    }

    SearchResponse.Results results = response.results;
    int totalResults = parseNumber(results.totalResults);
    int startIndex = parseNumber(results.startIndex);
    int itemsPerPage = parseNumber(results.itemsPerPage);

    List<Artist> artists = List.of();
    if (results.artistmatches != null && results.artistmatches.artist != null) {
      artists =
          results.artistmatches.artist.stream()
              .map(ArtistMapper::fromSearchArtist)
              .toList();
    }

    return new ArtistSearchResult(totalResults, startIndex, itemsPerPage, artists);
  }

  private static Artist fromSearchArtist(SearchResponse.ArtistData data) {
    if (data == null) {
      return null;
    }

    int listeners = parseNumber(data.listeners);
    Artist.Stats stats = new Artist.Stats(listeners, 0, null);

    return new Artist(
        data.name,
        data.mbid,
        data.url,
        mapImages(data.image, img -> new Artist.Image(img.getSize(), img.getUrl())),
        data.streamable,
        stats,
        List.of(),
        List.of(),
        null,
        null);
  }
}

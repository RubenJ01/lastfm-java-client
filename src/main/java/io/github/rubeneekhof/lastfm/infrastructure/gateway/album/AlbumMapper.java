package io.github.rubeneekhof.lastfm.infrastructure.gateway.album;

import io.github.rubeneekhof.lastfm.domain.model.Album;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.album.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AlbumMapper extends BaseMapper {

  public static Album from(GetInfoResponse response) {
    if (response == null || response.album == null) {
      return null;
    }

    GetInfoResponse.AlbumData data = response.album;
    return new Album(
        data.name,
        data.artist,
        data.id,
        data.mbid,
        data.url,
        extractReleaseDate(data),
        mapImages(data.image, img -> new Album.Image(img.getSize(), img.getUrl())),
        mapStats(data.listeners, data.playcount, data.userplaycount),
        mapTags(data.tags),
        mapTracks(data.tracks),
        mapWiki(
            data.wiki,
            wiki -> new Album.Wiki(wiki.getPublished(), wiki.getSummary(), wiki.getContent())));
  }

  private static String extractReleaseDate(GetInfoResponse.AlbumData data) {
    if (data.releasedate != null && !data.releasedate.isBlank()) {
      return data.releasedate;
    }
    if (data.wiki != null && data.wiki.published != null) {
      return data.wiki.published;
    }
    return null;
  }

  private static Album.Stats mapStats(String listeners, String playcount, Object userplaycount) {
    try {
      int listenersCount = parseNumber(listeners);
      int plays = parseNumber(playcount);
      Integer userPlays = null;
      if (userplaycount != null) {
        if (userplaycount instanceof Number) {
          userPlays = ((Number) userplaycount).intValue();
        } else if (userplaycount instanceof String userPlayStr) {
          if (!userPlayStr.isBlank()) {
            userPlays = parseNumber(userPlayStr);
          }
        }
      }
      return new Album.Stats(listenersCount, plays, userPlays);
    } catch (NumberFormatException e) {
      return new Album.Stats(0, 0, null);
    }
  }

  private static List<Album.Tag> mapTags(Object tags) {
    if (tags == null) {
      return List.of();
    }

    if (tags instanceof String) {
      return List.of();
    }

    if (tags instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, Object> tagsMap = (Map<String, Object>) tags;
      Object tagArray = tagsMap.get("tag");
      if (tagArray instanceof List) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> tagList = (List<Map<String, Object>>) tagArray;
        return tagList.stream()
            .map(tagMap -> new Album.Tag((String) tagMap.get("name"), (String) tagMap.get("url")))
            .toList();
      }
    }

    if (tags instanceof GetInfoResponse.Tags tagsObj) {
      return Optional.ofNullable(tagsObj.tag)
          .filter(list -> !list.isEmpty())
          .map(list -> list.stream().map(t -> new Album.Tag(t.name, t.url)).toList())
          .orElse(List.of());
    }

    return List.of();
  }

  private static List<Album.Track> mapTracks(GetInfoResponse.Tracks tracks) {
    return Optional.ofNullable(tracks)
        .map(t -> t.track)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(AlbumMapper::mapTrack).toList())
        .orElse(List.of());
  }

  private static Album.Track mapTrack(GetInfoResponse.Track track) {
    if (track == null) {
      return null;
    }

    Integer rank =
        track.attr != null && track.attr.rank != null ? parseNumber(track.attr.rank) : null;

    Integer duration = null;
    if (track.duration != null) {
      if (track.duration instanceof Number) {
        duration = ((Number) track.duration).intValue();
      } else if (track.duration instanceof String) {
        String durationStr = (String) track.duration;
        if (!durationStr.isBlank()) {
          duration = parseNumber(durationStr);
        }
      }
    }

    String artistName = track.artist != null ? track.artist.name : null;
    String artistMbid = track.artist != null ? track.artist.mbid : null;
    String artistUrl = track.artist != null ? track.artist.url : null;

    String streamable =
        track.streamable != null && track.streamable.text != null ? track.streamable.text : null;

    return new Album.Track(
        track.name,
        duration,
        track.mbid,
        track.url,
        streamable,
        artistName,
        artistMbid,
        artistUrl,
        rank);
  }
}

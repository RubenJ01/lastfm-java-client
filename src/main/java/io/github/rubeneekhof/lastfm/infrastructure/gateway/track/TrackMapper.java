package io.github.rubeneekhof.lastfm.infrastructure.gateway.track;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Track;
import io.github.rubeneekhof.lastfm.domain.model.scrobble.ScrobbleResult;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response.ScrobbleResponse;
import java.util.List;
import java.util.Map;

public class TrackMapper extends BaseMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static io.github.rubeneekhof.lastfm.domain.model.scrobble.ScrobbleResponse from(
      ScrobbleResponse response) {
    if (response == null || response.scrobbles == null) {
      return new io.github.rubeneekhof.lastfm.domain.model.scrobble.ScrobbleResponse(
          0, 0, List.of());
    }

    int accepted = parseInt(response.scrobbles.accepted);
    int ignored = parseInt(response.scrobbles.ignored);

    List<ScrobbleResult> results = parseScrobbles(response.scrobbles.scrobble);

    return new io.github.rubeneekhof.lastfm.domain.model.scrobble.ScrobbleResponse(
        accepted, ignored, results);
  }

  private static List<ScrobbleResult> parseScrobbles(Object scrobbleData) {
    if (scrobbleData == null) {
      return List.of();
    }

    if (scrobbleData instanceof List<?> list) {
      return list.stream()
          .filter(item -> item instanceof ScrobbleResponse.Scrobble)
          .map(item -> from((ScrobbleResponse.Scrobble) item))
          .toList();
    }

    if (scrobbleData instanceof Map) {
      try {
        ScrobbleResponse.Scrobble scrobble =
            objectMapper.convertValue(scrobbleData, ScrobbleResponse.Scrobble.class);
        return List.of(from(scrobble));
      } catch (Exception e) {
        return List.of();
      }
    }

    if (scrobbleData instanceof ScrobbleResponse.Scrobble) {
      return List.of(from((ScrobbleResponse.Scrobble) scrobbleData));
    }

    return List.of();
  }

  private static ScrobbleResult from(ScrobbleResponse.Scrobble scrobble) {
    if (scrobble == null) {
      return null;
    }

    String track = scrobble.track != null ? scrobble.track.text : null;
    String artist = scrobble.artist != null ? scrobble.artist.text : null;
    String album = scrobble.album != null ? scrobble.album.text : null;
    String albumArtist = scrobble.albumArtist != null ? scrobble.albumArtist.text : null;

    long timestamp = parseLong(scrobble.timestamp);

    boolean trackCorrected = scrobble.track != null && "1".equals(scrobble.track.corrected);
    boolean artistCorrected = scrobble.artist != null && "1".equals(scrobble.artist.corrected);
    boolean albumCorrected = scrobble.album != null && "1".equals(scrobble.album.corrected);
    boolean albumArtistCorrected =
        scrobble.albumArtist != null && "1".equals(scrobble.albumArtist.corrected);

    int ignoredCode = scrobble.ignoredMessage != null ? parseInt(scrobble.ignoredMessage.code) : 0;

    return new ScrobbleResult(
        track,
        artist,
        album,
        albumArtist,
        timestamp,
        trackCorrected,
        artistCorrected,
        albumCorrected,
        albumArtistCorrected,
        ignoredCode);
  }

  private static int parseInt(String value) {
    if (value == null || value.isBlank()) {
      return 0;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private static long parseLong(String value) {
    if (value == null || value.isBlank()) {
      return 0;
    }
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static Track from(GetInfoResponse response) {
    if (response == null || response.track == null) {
      return null;
    }

    GetInfoResponse.TrackData data = response.track;
    return new Track(
        data.name,
        data.mbid,
        data.url,
        parseDuration(data.duration),
        mapStreamable(data.streamable),
        mapStats(data.listeners, data.playcount),
        mapArtist(data.artist),
        mapAlbum(data.album),
        parseUserPlaycount(data.userplaycount),
        parseUserLoved(data.userloved),
        mapTags(data.toptags),
        mapWiki(data.wiki));
  }

  private static Integer parseDuration(String duration) {
    if (duration == null || duration.isBlank()) {
      return null;
    }
    try {
      return Integer.parseInt(duration);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private static Track.Streamable mapStreamable(GetInfoResponse.Streamable streamable) {
    if (streamable == null) {
      return null;
    }
    boolean text = "1".equals(streamable.text);
    boolean fulltrack = "1".equals(streamable.fulltrack);
    return new Track.Streamable(text, fulltrack);
  }

  private static Track.Stats mapStats(String listeners, String playcount) {
    long listenersCount = parseLong(listeners);
    long plays = parseLong(playcount);
    return new Track.Stats(listenersCount, plays);
  }

  private static Track.Artist mapArtist(GetInfoResponse.TrackArtist artist) {
    if (artist == null) {
      return null;
    }
    return new Track.Artist(artist.name, artist.mbid, artist.url);
  }

  private static Track.Album mapAlbum(GetInfoResponse.TrackAlbum album) {
    if (album == null) {
      return null;
    }
    Integer position = null;
    if (album.attr != null && album.attr.position != null) {
      try {
        position = Integer.parseInt(album.attr.position);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
    return new Track.Album(
        album.artist,
        album.title,
        album.mbid,
        album.url,
        mapImages(album.image, img -> new Track.Album.Image(img.getSize(), img.getUrl())),
        position);
  }

  private static Integer parseUserPlaycount(String userplaycount) {
    if (userplaycount == null || userplaycount.isBlank()) {
      return null;
    }
    try {
      return Integer.parseInt(userplaycount);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private static Boolean parseUserLoved(String userloved) {
    if (userloved == null || userloved.isBlank()) {
      return null;
    }
    return "1".equals(userloved);
  }

  private static List<Track.Tag> mapTags(GetInfoResponse.TopTags toptags) {
    if (toptags == null || toptags.tag == null) {
      return List.of();
    }
    return toptags.tag.stream().map(tag -> new Track.Tag(tag.name, tag.url)).toList();
  }

  private static Track.Wiki mapWiki(GetInfoResponse.Wiki wiki) {
    if (wiki == null) {
      return null;
    }
    return new Track.Wiki(wiki.published, wiki.summary, wiki.content);
  }
}

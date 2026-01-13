package io.github.rubeneekhof.lastfm.infrastructure.gateway.track;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.ScrobbleResult;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response.ScrobbleResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TrackMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse from(
      ScrobbleResponse response) {
    if (response == null || response.scrobbles == null) {
      return new io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse(0, 0, List.of());
    }

    int accepted = parseInt(response.scrobbles.accepted, 0);
    int ignored = parseInt(response.scrobbles.ignored, 0);

    List<ScrobbleResult> results = parseScrobbles(response.scrobbles.scrobble);

    return new io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse(
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

    String track = getText(scrobble.track);
    String artist = getText(scrobble.artist);
    String album = getText(scrobble.album);
    String albumArtist = getText(scrobble.albumArtist);
    long timestamp = parseLong(scrobble.timestamp, 0);
    boolean trackCorrected = "1".equals(getCorrected(scrobble.track));
    boolean artistCorrected = "1".equals(getCorrected(scrobble.artist));
    boolean albumCorrected = "1".equals(getCorrected(scrobble.album));
    boolean albumArtistCorrected = "1".equals(getCorrected(scrobble.albumArtist));
    int ignoredCode =
        Optional.ofNullable(scrobble.ignoredMessage).map(im -> parseInt(im.code, 0)).orElse(0);

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

  private static String getText(ScrobbleResponse.Track track) {
    return track != null ? track.text : null;
  }

  private static String getText(ScrobbleResponse.Artist artist) {
    return artist != null ? artist.text : null;
  }

  private static String getText(ScrobbleResponse.Album album) {
    return album != null ? album.text : null;
  }

  private static String getText(ScrobbleResponse.AlbumArtist albumArtist) {
    return albumArtist != null ? albumArtist.text : null;
  }

  private static String getCorrected(ScrobbleResponse.Track track) {
    return track != null ? track.corrected : null;
  }

  private static String getCorrected(ScrobbleResponse.Artist artist) {
    return artist != null ? artist.corrected : null;
  }

  private static String getCorrected(ScrobbleResponse.Album album) {
    return album != null ? album.corrected : null;
  }

  private static String getCorrected(ScrobbleResponse.AlbumArtist albumArtist) {
    return albumArtist != null ? albumArtist.corrected : null;
  }

  private static int parseInt(String value, int defaultValue) {
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  private static long parseLong(String value, long defaultValue) {
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }
}

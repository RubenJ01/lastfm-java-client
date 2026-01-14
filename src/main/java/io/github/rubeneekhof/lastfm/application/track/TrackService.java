package io.github.rubeneekhof.lastfm.application.track;

import io.github.rubeneekhof.lastfm.domain.model.Scrobble;
import io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse;
import io.github.rubeneekhof.lastfm.domain.model.Track;
import io.github.rubeneekhof.lastfm.domain.port.TrackGateway;

public class TrackService {

  private final TrackGateway gateway;

  public TrackService(TrackGateway gateway) {
    this.gateway = gateway;
  }

  /**
   * Get the metadata for a track on Last.fm using the artist/track name or a MusicBrainz ID.
   *
   * <p>No authentication required.
   *
   * <p>This is a convenience method that requires both artist and track names.
   *
   * @param artist the artist name to fetch information for (required)
   * @param track the track name to fetch information for (required)
   * @return the track metadata
   * @throws IllegalArgumentException if artist or track is null or blank
   * @see #getInfo(TrackGetInfoRequest) for more options including MBID, autocorrect, and username
   */
  public Track getInfo(String artist, String track) {
    if (artist == null || artist.isBlank()) {
      throw new IllegalArgumentException("Artist must not be blank");
    }
    if (track == null || track.isBlank()) {
      throw new IllegalArgumentException("Track must not be blank");
    }
    return getInfo(TrackGetInfoRequest.artist(artist).track(track).build());
  }

  /**
   * Get the metadata for a track on Last.fm with autocorrect option.
   *
   * <p>No authentication required.
   *
   * @param artist the artist name to fetch information for (required)
   * @param track the track name to fetch information for (required)
   * @param autocorrect transform misspelled artist names into correct artist names, returning the
   *     correct version instead. The corrected artist name will be returned in the response.
   * @return the track metadata
   * @throws IllegalArgumentException if artist or track is null or blank
   * @see #getInfo(TrackGetInfoRequest) for more options including MBID and username
   */
  public Track getInfo(String artist, String track, Boolean autocorrect) {
    if (artist == null || artist.isBlank()) {
      throw new IllegalArgumentException("Artist must not be blank");
    }
    if (track == null || track.isBlank()) {
      throw new IllegalArgumentException("Track must not be blank");
    }
    return getInfo(
        TrackGetInfoRequest.artist(artist).track(track).autocorrect(autocorrect).build());
  }

  /**
   * Get the metadata for a track on Last.fm with user-specific data.
   *
   * <p>No authentication required, but if a username is supplied, the user's playcount for this
   * track and whether they have loved the track is included in the response.
   *
   * @param artist the artist name to fetch information for (required)
   * @param track the track name to fetch information for (required)
   * @param username the username for the context of the request. If supplied, the user's playcount
   *     for this track and whether they have loved the track is included in the response.
   * @return the track metadata
   * @throws IllegalArgumentException if artist or track is null or blank
   * @see #getInfo(TrackGetInfoRequest) for more options including MBID and autocorrect
   */
  public Track getInfo(String artist, String track, String username) {
    if (artist == null || artist.isBlank()) {
      throw new IllegalArgumentException("Artist must not be blank");
    }
    if (track == null || track.isBlank()) {
      throw new IllegalArgumentException("Track must not be blank");
    }
    return getInfo(TrackGetInfoRequest.artist(artist).track(track).username(username).build());
  }

  /**
   * Get the metadata for a track on Last.fm using the artist/track name or a MusicBrainz ID.
   *
   * <p>No authentication required.
   *
   * <p>This method provides full control over all parameters. Use {@link TrackGetInfoRequest} to
   * build a request with the desired options:
   *
   * <pre>{@code
   * Track track = client.tracks().getInfo(
   *     TrackGetInfoRequest.artist("Linkin Park")
   *         .track("One Step Closer")
   *         .username("myusername")
   *         .autocorrect(true)
   *         .build()
   * );
   * }</pre>
   *
   * <p>Or using MBID instead of artist/track names:
   *
   * <pre>{@code
   * Track track = client.tracks().getInfo(
   *     TrackGetInfoRequest.mbid("30cb03f3-bd95-43b0-9d41-6d75e13cd353")
   *         .build()
   * );
   * }</pre>
   *
   * <p><strong>Parameters:</strong>
   *
   * <ul>
   *   <li><strong>artist</strong> (string, optional*): The artist name to fetch information for.
   *       Required unless using MBID.
   *   <li><strong>track</strong> (string, optional*): The track name to fetch information for.
   *       Required unless using MBID.
   *   <li><strong>mbid</strong> (string, optional): The track's MusicBrainz ID. If provided, artist
   *       and track parameters are not required.
   *   <li><strong>autocorrect</strong> (boolean, optional, default: false): Transform misspelled
   *       artist names into correct artist names, returning the correct version instead. The
   *       corrected artist name will be returned in the response.
   *   <li><strong>username</strong> (string, optional): The username for the context of the
   *       request. If supplied, the user's playcount for this track and whether they have loved the
   *       track is included in the response.
   * </ul>
   *
   * <p>* Required unless you are using a MusicBrainz ID for the track.
   *
   * @param request the request containing track identification and optional parameters
   * @return the track metadata, or null if the track is not found
   * @throws IllegalArgumentException if neither MBID nor both artist and track are provided
   */
  public Track getInfo(TrackGetInfoRequest request) {
    return gateway.getInfo(
        request.artist(),
        request.track(),
        request.mbid(),
        request.autocorrect(),
        request.username());
  }

  public ScrobbleResponse scrobble(Scrobble scrobble) {
    return scrobble(TrackScrobbleRequest.single(scrobble).build());
  }

  public ScrobbleResponse scrobble(TrackScrobbleRequest request) {
    return gateway.scrobble(request.scrobbles());
  }
}

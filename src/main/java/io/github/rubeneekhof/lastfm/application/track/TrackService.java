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

  public Track getInfo(String artist, String track) {
    if (artist == null || artist.isBlank()) {
      throw new IllegalArgumentException("Artist must not be blank");
    }
    if (track == null || track.isBlank()) {
      throw new IllegalArgumentException("Track must not be blank");
    }
    return getInfo(TrackGetInfoRequest.artist(artist).track(track).build());
  }

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

  public Track getInfo(String artist, String track, String username) {
    if (artist == null || artist.isBlank()) {
      throw new IllegalArgumentException("Artist must not be blank");
    }
    if (track == null || track.isBlank()) {
      throw new IllegalArgumentException("Track must not be blank");
    }
    return getInfo(TrackGetInfoRequest.artist(artist).track(track).username(username).build());
  }

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

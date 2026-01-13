package io.github.rubeneekhof.lastfm.application.track;

import io.github.rubeneekhof.lastfm.domain.model.Scrobble;
import io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse;
import io.github.rubeneekhof.lastfm.domain.port.TrackGateway;

public class TrackService {

  private final TrackGateway gateway;

  public TrackService(TrackGateway gateway) {
    this.gateway = gateway;
  }

  public ScrobbleResponse scrobble(Scrobble scrobble) {
    return scrobble(TrackScrobbleRequest.single(scrobble).build());
  }

  public ScrobbleResponse scrobble(TrackScrobbleRequest request) {
    return gateway.scrobble(request.scrobbles());
  }
}

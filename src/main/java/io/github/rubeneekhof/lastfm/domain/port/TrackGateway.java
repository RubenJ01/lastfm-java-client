package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Track;
import io.github.rubeneekhof.lastfm.domain.model.scrobble.Scrobble;
import io.github.rubeneekhof.lastfm.domain.model.scrobble.ScrobbleResponse;
import java.util.List;

public interface TrackGateway extends LastFmApiGateway {
  ScrobbleResponse scrobble(List<Scrobble> scrobbles);

  Track getInfo(String artist, String track, String mbid, Boolean autocorrect, String username);

  void love(String artist, String track);
}

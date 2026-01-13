package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Scrobble;
import io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse;
import java.util.List;

public interface TrackGateway {
  ScrobbleResponse scrobble(List<Scrobble> scrobbles);
}

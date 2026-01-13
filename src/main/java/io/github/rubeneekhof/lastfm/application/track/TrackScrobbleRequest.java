package io.github.rubeneekhof.lastfm.application.track;

import io.github.rubeneekhof.lastfm.domain.model.Scrobble;
import java.util.List;

public record TrackScrobbleRequest(List<Scrobble> scrobbles) {

  public static Builder builder() {
    return new Builder();
  }

  public static Builder single(Scrobble scrobble) {
    return new Builder().addScrobble(scrobble);
  }

  public static class Builder {
    private final java.util.List<Scrobble> scrobbles = new java.util.ArrayList<>();

    public Builder addScrobble(Scrobble scrobble) {
      if (scrobbles.size() >= 50) {
        throw new IllegalArgumentException("Maximum 50 scrobbles per batch");
      }
      scrobbles.add(scrobble);
      return this;
    }

    public Builder addScrobbles(List<Scrobble> scrobbles) {
      if (this.scrobbles.size() + scrobbles.size() > 50) {
        throw new IllegalArgumentException("Maximum 50 scrobbles per batch");
      }
      this.scrobbles.addAll(scrobbles);
      return this;
    }

    public TrackScrobbleRequest build() {
      if (scrobbles.isEmpty()) {
        throw new IllegalArgumentException("At least one scrobble must be provided");
      }
      return new TrackScrobbleRequest(List.copyOf(scrobbles));
    }
  }
}

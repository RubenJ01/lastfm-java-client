package io.github.rubeneekhof.lastfm.application.track;

public class TrackLoveRequest {
  private final String artist;
  private final String track;

  private TrackLoveRequest(Builder builder) {
    this.artist = builder.artist;
    this.track = builder.track;
  }

  public static Builder artist(String artist) {
    return new Builder().artist(artist);
  }

  public String artist() {
    return artist;
  }

  public String track() {
    return track;
  }

  public static class Builder {
    private String artist;
    private String track;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder track(String track) {
      this.track = track;
      return this;
    }

    public TrackLoveRequest build() {
      if (artist == null || artist.isBlank()) {
        throw new IllegalArgumentException("Artist must be provided");
      }
      if (track == null || track.isBlank()) {
        throw new IllegalArgumentException("Track must be provided");
      }
      return new TrackLoveRequest(this);
    }
  }
}

package io.github.rubeneekhof.lastfm.application.track;

public record TrackGetInfoRequest(
    String artist, String track, String mbid, Boolean autocorrect, String username) {

  public static Builder artist(String artist) {
    return new Builder().artist(artist);
  }

  public static Builder track(String track) {
    return new Builder().track(track);
  }

  public static Builder mbid(String mbid) {
    return new Builder().mbid(mbid);
  }

  public static class Builder {
    private String artist;
    private String track;
    private String mbid;
    private Boolean autocorrect;
    private String username;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder track(String track) {
      this.track = track;
      return this;
    }

    public Builder mbid(String mbid) {
      this.mbid = mbid;
      return this;
    }

    public Builder autocorrect(Boolean autocorrect) {
      this.autocorrect = autocorrect;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public TrackGetInfoRequest build() {
      if (mbid == null && (artist == null || track == null)) {
        throw new IllegalArgumentException(
            "Either mbid must be provided, or both artist and track must be provided");
      }
      return new TrackGetInfoRequest(artist, track, mbid, autocorrect, username);
    }
  }
}

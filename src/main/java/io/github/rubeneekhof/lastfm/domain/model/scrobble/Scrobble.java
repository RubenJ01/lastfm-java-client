package io.github.rubeneekhof.lastfm.domain.model.scrobble;

public record Scrobble(
    String track,
    String artist,
    long timestamp,
    String album,
    String albumArtist,
    String mbid,
    Integer trackNumber,
    Integer duration,
    Boolean chosenByUser,
    String context,
    String streamId) {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String track;
    private String artist;
    private Long timestamp;
    private String album;
    private String albumArtist;
    private String mbid;
    private Integer trackNumber;
    private Integer duration;
    private Boolean chosenByUser;
    private String context;
    private String streamId;

    public Builder track(String track) {
      this.track = track;
      return this;
    }

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder timestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder album(String album) {
      this.album = album;
      return this;
    }

    public Builder albumArtist(String albumArtist) {
      this.albumArtist = albumArtist;
      return this;
    }

    public Builder mbid(String mbid) {
      this.mbid = mbid;
      return this;
    }

    public Builder trackNumber(int trackNumber) {
      this.trackNumber = trackNumber;
      return this;
    }

    public Builder duration(int duration) {
      this.duration = duration;
      return this;
    }

    public Builder chosenByUser(boolean chosenByUser) {
      this.chosenByUser = chosenByUser;
      return this;
    }

    public Builder context(String context) {
      this.context = context;
      return this;
    }

    public Builder streamId(String streamId) {
      this.streamId = streamId;
      return this;
    }

    public Scrobble build() {
      if (track == null || track.isBlank()) {
        throw new IllegalArgumentException("Track must not be blank");
      }
      if (artist == null || artist.isBlank()) {
        throw new IllegalArgumentException("Artist must not be blank");
      }
      if (timestamp == null) {
        throw new IllegalArgumentException("Timestamp must be provided");
      }
      return new Scrobble(
          track,
          artist,
          timestamp,
          album,
          albumArtist,
          mbid,
          trackNumber,
          duration,
          chosenByUser,
          context,
          streamId);
    }
  }
}

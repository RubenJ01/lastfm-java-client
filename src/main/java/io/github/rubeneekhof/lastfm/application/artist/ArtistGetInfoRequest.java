package io.github.rubeneekhof.lastfm.application.artist;

public class ArtistGetInfoRequest {
  private final String artist;
  private final String mbid;
  private final String lang;
  private final Boolean autocorrect;
  private final String username;

  private ArtistGetInfoRequest(Builder builder) {
    this.artist = builder.artist;
    this.mbid = builder.mbid;
    this.lang = builder.lang;
    this.autocorrect = builder.autocorrect;
    this.username = builder.username;
  }

  public static Builder artist(String artist) {
    return new Builder().artist(artist);
  }

  public static Builder mbid(String mbid) {
    return new Builder().mbid(mbid);
  }

  public String artist() {
    return artist;
  }

  public String mbid() {
    return mbid;
  }

  public String lang() {
    return lang;
  }

  public Boolean autocorrect() {
    return autocorrect;
  }

  public String username() {
    return username;
  }

  public static class Builder {
    private String artist;
    private String mbid;
    private String lang;
    private Boolean autocorrect;
    private String username;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder mbid(String mbid) {
      this.mbid = mbid;
      return this;
    }

    public Builder lang(String lang) {
      this.lang = lang;
      return this;
    }

    public Builder autocorrect(boolean autocorrect) {
      this.autocorrect = autocorrect;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public ArtistGetInfoRequest build() {
      if (artist == null && mbid == null) {
        throw new IllegalArgumentException("Either artist name or mbid must be provided");
      }
      return new ArtistGetInfoRequest(this);
    }
  }
}

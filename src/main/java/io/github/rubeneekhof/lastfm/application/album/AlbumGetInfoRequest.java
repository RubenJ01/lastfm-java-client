package io.github.rubeneekhof.lastfm.application.album;

public class AlbumGetInfoRequest {
  private final String artist;
  private final String album;
  private final String mbid;
  private final Boolean autocorrect;
  private final String username;
  private final String lang;

  private AlbumGetInfoRequest(Builder builder) {
    this.artist = builder.artist;
    this.album = builder.album;
    this.mbid = builder.mbid;
    this.autocorrect = builder.autocorrect;
    this.username = builder.username;
    this.lang = builder.lang;
  }

  public static Builder artist(String artist) {
    return new Builder().artist(artist);
  }

  public static Builder album(String album) {
    return new Builder().album(album);
  }

  public static Builder mbid(String mbid) {
    return new Builder().mbid(mbid);
  }

  public String artist() {
    return artist;
  }

  public String album() {
    return album;
  }

  public String mbid() {
    return mbid;
  }

  public Boolean autocorrect() {
    return autocorrect;
  }

  public String username() {
    return username;
  }

  public String lang() {
    return lang;
  }

  public static class Builder {
    private String artist;
    private String album;
    private String mbid;
    private Boolean autocorrect;
    private String username;
    private String lang;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder album(String album) {
      this.album = album;
      return this;
    }

    public Builder mbid(String mbid) {
      this.mbid = mbid;
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

    public Builder lang(String lang) {
      this.lang = lang;
      return this;
    }

    public AlbumGetInfoRequest build() {
      if (artist == null && album == null && mbid == null) {
        throw new IllegalArgumentException("Either artist/album name or mbid must be provided");
      }
      return new AlbumGetInfoRequest(this);
    }
  }
}

package io.github.rubeneekhof.lastfm.application.artist;

public class ArtistSearchRequest {
  private final String artist;
  private final Integer limit;
  private final Integer page;

  private ArtistSearchRequest(Builder builder) {
    this.artist = builder.artist;
    this.limit = builder.limit;
    this.page = builder.page;
  }

  public static Builder artist(String artist) {
    return new Builder().artist(artist);
  }

  public String artist() {
    return artist;
  }

  public Integer limit() {
    return limit;
  }

  public Integer page() {
    return page;
  }

  public static class Builder {
    private String artist;
    private Integer limit;
    private Integer page;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    public Builder page(int page) {
      this.page = page;
      return this;
    }

    public ArtistSearchRequest build() {
      if (artist == null || artist.isBlank()) {
        throw new IllegalArgumentException("Artist name must be provided");
      }
      return new ArtistSearchRequest(this);
    }
  }
}

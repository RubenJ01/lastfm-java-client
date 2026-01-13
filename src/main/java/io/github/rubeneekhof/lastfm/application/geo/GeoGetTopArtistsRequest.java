package io.github.rubeneekhof.lastfm.application.geo;

public record GeoGetTopArtistsRequest(String country, Integer page, Integer limit) {

  public static Builder country(String country) {
    return new Builder().country(country);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String country;
    private Integer page;
    private Integer limit;

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public Builder page(int page) {
      this.page = page;
      return this;
    }

    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    public GeoGetTopArtistsRequest build() {
      if (country == null || country.isBlank()) {
        throw new IllegalArgumentException("Country must not be blank");
      }
      return new GeoGetTopArtistsRequest(country, page, limit);
    }
  }
}

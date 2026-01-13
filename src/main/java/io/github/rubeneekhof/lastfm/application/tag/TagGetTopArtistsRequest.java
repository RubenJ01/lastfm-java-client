package io.github.rubeneekhof.lastfm.application.tag;

public record TagGetTopArtistsRequest(String tag, Integer page, Integer limit) {

  public static Builder tag(String tag) {
    return new Builder().tag(tag);
  }

  public static Builder page(int page) {
    return new Builder().page(page);
  }

  public static Builder limit(int limit) {
    return new Builder().limit(limit);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String tag;
    private Integer page;
    private Integer limit;

    public Builder tag(String tag) {
      this.tag = tag;
      return this;
    }

    public Builder page(Integer page) {
      this.page = page;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public TagGetTopArtistsRequest build() {
      if (tag == null) {
        throw new IllegalArgumentException("Tag must be provided");
      }
      return new TagGetTopArtistsRequest(tag, page, limit);
    }
  }
}

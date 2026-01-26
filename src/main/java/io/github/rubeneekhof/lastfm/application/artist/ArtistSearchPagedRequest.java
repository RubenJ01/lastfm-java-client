package io.github.rubeneekhof.lastfm.application.artist;

/**
 * Request builder for paginated artist search operations.
 * Use this when you want to iterate over multiple pages of results.
 */
public class ArtistSearchPagedRequest {
  private final String artist;
  private final Integer pageSize;
  private final Integer maxItems;

  private ArtistSearchPagedRequest(Builder builder) {
    this.artist = builder.artist;
    this.pageSize = builder.pageSize;
    this.maxItems = builder.maxItems;
  }

  public static Builder artist(String artist) {
    return new Builder().artist(artist);
  }

  public String artist() {
    return artist;
  }

  /**
   * Returns the page size (items per API call). If null, uses default.
   */
  public Integer pageSize() {
    return pageSize;
  }

  /**
   * Returns the maximum number of items to return across all pages. If null, returns all items.
   */
  public Integer maxItems() {
    return maxItems;
  }

  public static class Builder {
    private String artist;
    private Integer pageSize;
    private Integer maxItems;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    /**
     * Sets the page size (number of items to fetch per API call).
     * This controls how many items each API request returns.
     *
     * @param pageSize the number of items per page (must be greater than 0)
     * @return this builder
     */
    public Builder pageSize(int pageSize) {
      if (pageSize <= 0) {
        throw new IllegalArgumentException("Page size must be greater than zero");
      }
      this.pageSize = pageSize;
      return this;
    }

    /**
     * Sets the maximum number of items to return across all pages.
     * The paginator will stop fetching pages once this limit is reached.
     *
     * @param maxItems the maximum number of items to return (must be greater than 0)
     * @return this builder
     */
    public Builder maxItems(int maxItems) {
      if (maxItems <= 0) {
        throw new IllegalArgumentException("Max items must be greater than zero");
      }
      this.maxItems = maxItems;
      return this;
    }

    public ArtistSearchPagedRequest build() {
      if (artist == null || artist.isBlank()) {
        throw new IllegalArgumentException("Artist name must be provided");
      }
      return new ArtistSearchPagedRequest(this);
    }
  }
}

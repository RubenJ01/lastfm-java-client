package io.github.rubeneekhof.lastfm.application.user;

/**
 * Request builder for paginated user friends operations.
 * Use this when you want to iterate over multiple pages of friends.
 */
public class UserGetFriendsPagedRequest {
  private final String user;
  private final Boolean recenttracks;
  private final Integer pageSize;
  private final Integer maxItems;

  private UserGetFriendsPagedRequest(Builder builder) {
    this.user = builder.user;
    this.recenttracks = builder.recenttracks;
    this.pageSize = builder.pageSize;
    this.maxItems = builder.maxItems;
  }

  public static Builder user(String user) {
    return new Builder().user(user);
  }

  public String user() {
    return user;
  }

  public Boolean recenttracks() {
    return recenttracks;
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
    private String user;
    private Boolean recenttracks;
    private Integer pageSize;
    private Integer maxItems;

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder recenttracks(boolean recenttracks) {
      this.recenttracks = recenttracks;
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

    public UserGetFriendsPagedRequest build() {
      if (user == null || user.isBlank()) {
        throw new IllegalArgumentException("User must be provided");
      }
      return new UserGetFriendsPagedRequest(this);
    }
  }
}

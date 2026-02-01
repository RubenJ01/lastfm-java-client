package io.github.rubeneekhof.lastfm.application.user;

public class UserGetLovedTracksPagedRequest {
  private final String user;
  private final Integer pageSize;
  private final Integer maxItems;

  private UserGetLovedTracksPagedRequest(Builder builder) {
    this.user = builder.user;
    this.pageSize = builder.pageSize;
    this.maxItems = builder.maxItems;
  }

  public static Builder user(String user) {
    return new Builder().user(user);
  }

  public String user() {
    return user;
  }

  public Integer pageSize() {
    return pageSize;
  }

  public Integer maxItems() {
    return maxItems;
  }

  public static class Builder {
    private String user;
    private Integer pageSize;
    private Integer maxItems;

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder pageSize(int pageSize) {
      if (pageSize <= 0) {
        throw new IllegalArgumentException("Page size must be greater than zero");
      }
      this.pageSize = pageSize;
      return this;
    }

    public Builder maxItems(int maxItems) {
      if (maxItems <= 0) {
        throw new IllegalArgumentException("Max items must be greater than zero");
      }
      this.maxItems = maxItems;
      return this;
    }

    public UserGetLovedTracksPagedRequest build() {
      if (user == null || user.isBlank()) {
        throw new IllegalArgumentException("User must be provided");
      }
      return new UserGetLovedTracksPagedRequest(this);
    }
  }
}

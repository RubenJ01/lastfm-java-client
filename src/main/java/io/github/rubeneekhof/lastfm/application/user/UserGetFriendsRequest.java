package io.github.rubeneekhof.lastfm.application.user;

public class UserGetFriendsRequest {
  private final String user;
  private final Boolean recenttracks;
  private final Integer limit;
  private final Integer page;

  private UserGetFriendsRequest(Builder builder) {
    this.user = builder.user;
    this.recenttracks = builder.recenttracks;
    this.limit = builder.limit;
    this.page = builder.page;
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

  public Integer limit() {
    return limit;
  }

  public Integer page() {
    return page;
  }

  public static class Builder {
    private String user;
    private Boolean recenttracks;
    private Integer limit;
    private Integer page;

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder recenttracks(boolean recenttracks) {
      this.recenttracks = recenttracks;
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

    public UserGetFriendsRequest build() {
      if (user == null || user.isBlank()) {
        throw new IllegalArgumentException("User must be provided");
      }
      if (page != null && page <= 0) {
        throw new IllegalArgumentException("Page must be greater than zero");
      }
      if (limit != null && limit <= 0) {
        throw new IllegalArgumentException("Limit must be greater than zero");
      }
      return new UserGetFriendsRequest(this);
    }
  }
}

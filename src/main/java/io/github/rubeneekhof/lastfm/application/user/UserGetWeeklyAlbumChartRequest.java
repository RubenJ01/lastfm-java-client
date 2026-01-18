package io.github.rubeneekhof.lastfm.application.user;

public class UserGetWeeklyAlbumChartRequest {
  private final String user;
  private final Integer limit;
  private final Long from;
  private final Long to;

  private UserGetWeeklyAlbumChartRequest(Builder builder) {
    this.user = builder.user;
    this.limit = builder.limit;
    this.from = builder.from;
    this.to = builder.to;
  }

  public static Builder user(String user) {
    return new Builder().user(user);
  }

  public String user() {
    return user;
  }

  public Integer limit() {
    return limit;
  }

  public Long from() {
    return from;
  }

  public Long to() {
    return to;
  }

  public static class Builder {
    private String user;
    private Integer limit;
    private Long from;
    private Long to;

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    public Builder from(long from) {
      this.from = from;
      return this;
    }

    public Builder to(long to) {
      this.to = to;
      return this;
    }

    public UserGetWeeklyAlbumChartRequest build() {
      if (user == null || user.isBlank()) {
        throw new IllegalArgumentException("User must be provided");
      }
      if ((from != null && to == null) || (from == null && to != null)) {
        throw new IllegalArgumentException(
            "Both 'from' and 'to' must be provided together, or both must be omitted");
      }
      return new UserGetWeeklyAlbumChartRequest(this);
    }
  }
}

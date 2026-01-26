package io.github.rubeneekhof.lastfm.application.user;

import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.domain.model.user.FriendsResult;
import io.github.rubeneekhof.lastfm.domain.model.user.WeeklyAlbumChart;
import io.github.rubeneekhof.lastfm.domain.port.UserGateway;
import io.github.rubeneekhof.lastfm.util.pagination.Page;
import io.github.rubeneekhof.lastfm.util.pagination.PageFetcher;
import io.github.rubeneekhof.lastfm.util.pagination.Paginator;
import io.github.rubeneekhof.lastfm.util.pagination.PaginationUtils;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Function;

public class UserService {

  private final UserGateway gateway;

  public UserService(UserGateway gateway) {
    this.gateway = gateway;
  }

  public User getInfo(String user) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    return gateway.getInfo(user);
  }

  /**
   * Get an album chart for a user profile, for a given date range. If no date range is supplied,
   * it will return the most recent album chart for this user.
   *
   * <p>No authentication required.
   *
   * <p>This is a convenience method that only requires the username.
   *
   * @param user the Last.fm username to fetch the charts of (required)
   * @return the weekly album chart
   * @throws IllegalArgumentException if user is null or blank
   * @see #getWeeklyAlbumChart(UserGetWeeklyAlbumChartRequest) for more options including limit and
   *     date range
   */
  public WeeklyAlbumChart getWeeklyAlbumChart(String user) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    return getWeeklyAlbumChart(UserGetWeeklyAlbumChartRequest.user(user).build());
  }

  /**
   * Get an album chart for a user profile with a limit.
   *
   * <p>No authentication required.
   *
   * @param user the Last.fm username to fetch the charts of (required)
   * @param limit the number of results to fetch. Maximum 1000.
   * @return the weekly album chart
   * @throws IllegalArgumentException if user is null or blank, or if limit is invalid
   * @see #getWeeklyAlbumChart(UserGetWeeklyAlbumChartRequest) for more options including date
   *     range
   */
  public WeeklyAlbumChart getWeeklyAlbumChart(String user, int limit) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    validateLimit(limit);
    return getWeeklyAlbumChart(
        UserGetWeeklyAlbumChartRequest.user(user).limit(limit).build());
  }

  /**
   * Get an album chart for a user profile with a date range.
   *
   * <p>No authentication required.
   *
   * <p><strong>Note:</strong> Both 'from' and 'to' must be provided together, or both must be
   * omitted.
   *
   * <p>Example using {@link io.github.rubeneekhof.lastfm.util.UnixTime}:
   *
   * <pre>{@code
   * // Get last 7 days chart
   * var range = UnixTime.lastDays(7);
   * WeeklyAlbumChart chart = client.users().getWeeklyAlbumChart("RubenJ01", range.from(), range.to());
   *
   * // Or with specific dates
   * long from = UnixTime.at("2024-01-15");
   * long to = UnixTime.at("2024-01-22");
   * WeeklyAlbumChart chart = client.users().getWeeklyAlbumChart("RubenJ01", from, to);
   *
   * // Or with date range
   * getWeeklyAlbumChart("RubenJ01",
   *     UnixTime.at("2026-01-01"),
   *     UnixTime.now()
   * );
   * }</pre>
   *
   * @param user the Last.fm username to fetch the charts of (required)
   * @param from the date at which the chart should start from (UNIX timestamp)
   * @param to the date at which the chart should end on (UNIX timestamp)
   * @return the weekly album chart
   * @throws IllegalArgumentException if user is null or blank, or if only one of from/to is
   *     provided
   * @see #getWeeklyAlbumChart(UserGetWeeklyAlbumChartRequest) for more options including limit
   * @see io.github.rubeneekhof.lastfm.util.UnixTime for convenient timestamp utilities
   */
  public WeeklyAlbumChart getWeeklyAlbumChart(String user, long from, long to) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    return getWeeklyAlbumChart(
        UserGetWeeklyAlbumChartRequest.user(user).from(from).to(to).build());
  }

  /**
   * Get an album chart for a user profile with all options.
   *
   * <p>No authentication required.
   *
   * <p>This method provides full control using the builder pattern:
   *
   * <pre>{@code
   * // Using raw timestamps
   * WeeklyAlbumChart chart = client.users().getWeeklyAlbumChart(
   *     UserGetWeeklyAlbumChartRequest.user("RubenJ01")
   *         .limit(50)
   *         .from(1602504000L)
   *         .to(1603108800L)
   *         .build()
   * );
   *
   * // Using UnixTime helper (recommended)
   * var range = UnixTime.lastDays(7);
   * WeeklyAlbumChart chart = client.users().getWeeklyAlbumChart(
   *     UserGetWeeklyAlbumChartRequest.user("RubenJ01")
   *         .limit(50)
   *         .from(range.from())
   *         .to(range.to())
   *         .build()
   * );
   * }</pre>
   *
   * @param request the request containing user and optional parameters
   * @return the weekly album chart
   * @see io.github.rubeneekhof.lastfm.util.UnixTime for convenient timestamp utilities
   */
  public WeeklyAlbumChart getWeeklyAlbumChart(UserGetWeeklyAlbumChartRequest request) {
    return gateway.getWeeklyAlbumChart(
        request.user(), request.limit(), request.from(), request.to());
  }

  private void validateLimit(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("Limit must be greater than zero");
    }
    if (limit > 1000) {
      throw new IllegalArgumentException("Limit cannot exceed 1000");
    }
  }

  /**
   * Get a list of the user's friends (follow each other) on Last.fm.
   *
   * <p>No authentication required.
   *
   * <p>This is a convenience method that only requires the username.
   *
   * @param user the Last.fm username to fetch the friends of (required)
   * @return the friends result with pagination info
   * @throws IllegalArgumentException if user is null or blank
   * @see #getFriends(UserGetFriendsRequest) for more options including pagination
   */
  public FriendsResult getFriends(String user) {
    return getFriends(UserGetFriendsRequest.user(user).build());
  }

  /**
   * Get a list of the user's friends with pagination options.
   *
   * <p>No authentication required.
   *
   * @param user the Last.fm username to fetch the friends of (required)
   * @param recenttracks whether to include information about friends' recent listening in
   *     the response
   * @param limit the number of results to fetch per page (optional, default: 50)
   * @param page the page number to fetch (optional, default: 1)
   * @return the friends result with pagination info
   * @throws IllegalArgumentException if user is null or blank, or if page/limit are invalid
   * @see #getFriends(UserGetFriendsRequest) for builder pattern usage
   */
  public FriendsResult getFriends(String user, Boolean recenttracks, Integer limit, Integer page) {
    UserGetFriendsRequest.Builder builder = UserGetFriendsRequest.user(user);
    if (recenttracks != null) {
      builder.recenttracks(recenttracks);
    }
    if (limit != null) {
      builder.limit(limit);
    }
    if (page != null) {
      builder.page(page);
    }
    return getFriends(builder.build());
  }

  /**
   * Get a list of the user's friends with all options using the builder pattern.
   *
   * <p>No authentication required.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FriendsResult friends = client.users().getFriends(
   *     UserGetFriendsRequest.user("aidan-")
   *         .recenttracks(true)
   *         .limit(10)
   *         .page(1)
   *         .build()
   * );
   * }</pre>
   *
   * @param request the request containing user and optional parameters
   * @return the friends result with pagination info
   */
  public FriendsResult getFriends(UserGetFriendsRequest request) {
    return gateway.getFriends(
        request.user(), request.recenttracks(), request.limit(), request.page());
  }

  private static final int DEFAULT_PAGE_SIZE = 50;
  private static final int API_MAX_PAGE_SIZE = 50; // Maximum items per page allowed by Last.fm API

  /**
   * Creates a pagination helper for getting user friends. Provides convenient methods for iterating
   * over all pages of friends results.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * // Iterate over all friends
   * for (Friend friend : client.users().getFriendsPaged("aidan-").iterateAll()) {
   *   // process friend
   * }
   *
   * // Get first 100 friends
   * List<Friend> friends = client.users().getFriendsPaged("aidan-").toList(100);
   * }</pre>
   *
   * @param user the Last.fm username to fetch the friends of (required)
   * @return a pagination wrapper for the friends results
   * @throws IllegalArgumentException if user is null or blank
   */
  public Paginator<FriendsResult.Friend> getFriendsPaged(String user) {
    return getFriendsPaged(user, false, DEFAULT_PAGE_SIZE);
  }

  /**
   * Creates a pagination helper for getting user friends with all parameters.
   *
   * @param user the Last.fm username to fetch the friends of (required)
   * @param recenttracks whether to include information about friends' recent listening in the
   *     response
   * @param pageSize the number of results per page
   * @return a pagination wrapper for the friends results
   * @throws IllegalArgumentException if user is null or blank, or if pageSize is invalid
   */
  public Paginator<FriendsResult.Friend> getFriendsPaged(String user, boolean recenttracks, int pageSize) {
    return getFriendsPaged(
        UserGetFriendsRequest.user(user)
            .recenttracks(recenttracks)
            .limit(pageSize)
            .build());
  }

  /**
   * Creates a pagination helper for getting user friends using the builder pattern.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * // Using regular request (for backward compatibility)
   * Paginator<Friend> wrapper = client.users().getFriendsPaged(
   *     UserGetFriendsRequest.user("aidan-")
   *         .recenttracks(true)
   *         .limit(50)
   *         .build()
   * );
   *
   * // Using paginated request (recommended)
   * Paginator<Friend> wrapper = client.users().getFriendsPaged(
   *     UserGetFriendsPagedRequest.user("aidan-")
   *         .recenttracks(true)
   *         .pageSize(50)
   *         .maxItems(100)
   *         .build()
   * );
   * }</pre>
   *
   * @param request the friends request (page parameter is ignored, pagination is handled internally)
   * @return a pagination wrapper for the friends results
   */
  public Paginator<FriendsResult.Friend> getFriendsPaged(UserGetFriendsRequest request) {
    String user = request.user();
    boolean recenttracks = request.recenttracks() != null ? request.recenttracks() : false;
    int pageSize = request.limit() != null ? request.limit() : DEFAULT_PAGE_SIZE;
    
    PageFetcher<FriendsResult.Friend> pageFetcher = page -> {
      UserGetFriendsRequest pageRequest = UserGetFriendsRequest.user(user)
          .recenttracks(recenttracks)
          .limit(pageSize)
          .page(page)
          .build();
      FriendsResult result = getFriends(pageRequest);
      return new Page<>(
          result.friends(),
          result.page(),
          result.perPage(),
          Optional.of(result.totalPages()),
          Optional.of(result.total()));
    };
    
    Function<Integer, PageFetcher<FriendsResult.Friend>> pageSizeModifier = newPageSize -> page -> {
      UserGetFriendsRequest pageRequest = UserGetFriendsRequest.user(user)
          .recenttracks(recenttracks)
          .limit(newPageSize)
          .page(page)
          .build();
      FriendsResult result = getFriends(pageRequest);
      return new Page<>(
          result.friends(),
          result.page(),
          result.perPage(),
          Optional.of(result.totalPages()),
          Optional.of(result.total()));
    };
    
    return new Paginator<>(pageFetcher, pageSizeModifier);
  }

  /**
   * Creates a pagination helper for getting user friends using the paginated request builder.
   * This is the recommended way to create paginators as it clearly separates page size from max items.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * // Fetch 50 items per page, but only return first 100 items total
   * Paginator<Friend> wrapper = client.users().getFriendsPaged(
   *     UserGetFriendsPagedRequest.user("aidan-")
   *         .recenttracks(true)
   *         .pageSize(50)
   *         .maxItems(100)
   *         .build()
   * );
   *
   * // Just limit total items to 5
   * Paginator<Friend> wrapper = client.users().getFriendsPaged(
   *     UserGetFriendsPagedRequest.user("aidan-")
   *         .maxItems(5)
   *         .build()
   * );
   * }</pre>
   *
   * @param request the paginated friends request
   * @return a pagination wrapper for the friends results
   */
  public Paginator<FriendsResult.Friend> getFriendsPaged(UserGetFriendsPagedRequest request) {
    String user = request.user();
    boolean recenttracks = request.recenttracks() != null ? request.recenttracks() : false;
    Integer requestedPageSize = request.pageSize();
    Integer maxItems = request.maxItems();
    
    // Optimize page size to minimize API calls when maxItems is set
    int optimalPageSize =
        PaginationUtils.optimizePageSize(requestedPageSize, maxItems, DEFAULT_PAGE_SIZE, API_MAX_PAGE_SIZE);
    
    PageFetcher<FriendsResult.Friend> pageFetcher = page -> {
      UserGetFriendsRequest pageRequest = UserGetFriendsRequest.user(user)
          .recenttracks(recenttracks)
          .limit(optimalPageSize)
          .page(page)
          .build();
      FriendsResult result = getFriends(pageRequest);
      return new Page<>(
          result.friends(),
          result.page(),
          result.perPage(),
          Optional.of(result.totalPages()),
          Optional.of(result.total()));
    };
    
    Function<Integer, PageFetcher<FriendsResult.Friend>> pageSizeModifier = newPageSize -> page -> {
      UserGetFriendsRequest pageRequest = UserGetFriendsRequest.user(user)
          .recenttracks(recenttracks)
          .limit(newPageSize)
          .page(page)
          .build();
      FriendsResult result = getFriends(pageRequest);
      return new Page<>(
          result.friends(),
          result.page(),
          result.perPage(),
          Optional.of(result.totalPages()),
          Optional.of(result.total()));
    };
    
    return new Paginator<>(pageFetcher, pageSizeModifier, maxItems);
  }

}

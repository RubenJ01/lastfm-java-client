package io.github.rubeneekhof.lastfm.util.pagination;

/**
 * Utility methods for pagination operations.
 */
public final class PaginationUtils {

  private PaginationUtils() {
    // Utility class
  }

  /**
   * Optimizes the page size to minimize API calls when maxItems is set.
   * If maxItems is set and the requested page size is smaller, increases it to reduce the number of requests.
   *
   * @param requestedPageSize the user-requested page size (can be null)
   * @param maxItems the maximum items to return (can be null)
   * @param defaultPageSize the default page size to use if requestedPageSize is null
   * @param apiMaxLimit the maximum items allowed per page by the API
   * @return the optimized page size
   */
  public static int optimizePageSize(
      Integer requestedPageSize, Integer maxItems, int defaultPageSize, int apiMaxLimit) {
    int basePageSize = requestedPageSize != null ? requestedPageSize : defaultPageSize;

    // If maxItems is set, optimize to minimize requests
    if (maxItems != null && maxItems > basePageSize) {
      // Use the larger of: requested page size, or min(maxItems, API max limit)
      // This minimizes API calls while respecting user preferences
      return Math.max(basePageSize, Math.min(maxItems, apiMaxLimit));
    }

    return basePageSize;
  }
}

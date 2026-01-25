package io.github.rubeneekhof.lastfm.util.pagination;

/**
 * Functional interface for fetching a specific page of results.
 *
 * @param <T> the type of items in the page
 */
@FunctionalInterface
public interface PageFetcher<T> {
  /**
   * Fetches a specific page of results.
   *
   * @param pageNumber the page number to fetch (1-based)
   * @return the page of results
   */
  Page<T> fetch(int pageNumber);
}

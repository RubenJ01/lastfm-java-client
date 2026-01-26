package io.github.rubeneekhof.lastfm.util.pagination;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Generic paginator that provides convenient methods for working with paginated results.
 * Wraps a {@link PageFetcher} with helper methods for iterating, streaming, and collecting pages.
 *
 * @param <T> the type of items in the paginated results
 */
public final class Paginator<T> {

  private final PageFetcher<T> pageFetcher;
  private final Function<Integer, PageFetcher<T>> pageSizeModifier;
  private final Integer maxItems;

  /**
   * Creates a new paginator with the given page fetcher.
   *
   * @param pageFetcher the function to fetch pages
   */
  public Paginator(PageFetcher<T> pageFetcher) {
    this(pageFetcher, null, null);
  }

  /**
   * Creates a new paginator with the given page fetcher and optional page size modifier.
   *
   * @param pageFetcher the function to fetch pages
   * @param pageSizeModifier optional function to create a new PageFetcher with a different page size
   */
  public Paginator(PageFetcher<T> pageFetcher, Function<Integer, PageFetcher<T>> pageSizeModifier) {
    this(pageFetcher, pageSizeModifier, null);
  }

  /**
   * Creates a new paginator with the given page fetcher, optional page size modifier, and optional max items limit.
   *
   * @param pageFetcher the function to fetch pages
   * @param pageSizeModifier optional function to create a new PageFetcher with a different page size
   * @param maxItems optional maximum number of items to return across all pages
   */
  public Paginator(PageFetcher<T> pageFetcher, Function<Integer, PageFetcher<T>> pageSizeModifier, Integer maxItems) {
    if (pageFetcher == null) {
      throw new IllegalArgumentException("PageFetcher must not be null");
    }
    if (maxItems != null && maxItems <= 0) {
      throw new IllegalArgumentException("Max items must be greater than zero");
    }
    this.pageFetcher = pageFetcher;
    this.pageSizeModifier = pageSizeModifier;
    this.maxItems = maxItems;
  }

  /**
   * Fetches a specific page of results.
   *
   * @param page the page number (1-based)
   * @return a page of results
   */
  public Page<T> page(int page) {
    return pageFetcher.fetch(page);
  }

  /**
   * Returns an iterable that automatically fetches and iterates over all pages of results.
   * If a max items limit was set when creating the paginator, it will be respected.
   * Can be used with enhanced for-each loops.
   *
   * <p>Example:
   * <pre>{@code
   * for (Item item : paginator.iterateAll()) {
   *   // process item
   * }
   * }</pre>
   *
   * @return an iterable over all items (or up to maxItems if set)
   */
  public Iterable<T> iterateAll() {
    if (maxItems != null) {
      return PaginationHelper.iterableItems(pageFetcher, maxItems);
    }
    return PaginationHelper.iterableAll(pageFetcher);
  }

  /**
   * Creates a new paginator with the specified page size (items per API call).
   * This controls how many items are fetched per page request.
   *
   * <p>Example:
   * <pre>{@code
   * // Fetch 50 items per page instead of default
   * Paginator<Artist> paginator = client.artists().searchPaged("Low").limit(50);
   * for (Artist artist : paginator.iterateAll()) {
   *   // Each API call will fetch 50 items
   * }
   * }</pre>
   *
   * @param pageSize the number of items to fetch per page (must be greater than 0)
   * @return a new paginator with the specified page size
   * @throws IllegalArgumentException if pageSize is invalid or if page size modification is not supported
   */
  public Paginator<T> limit(int pageSize) {
    if (pageSize <= 0) {
      throw new IllegalArgumentException("Page size must be greater than zero");
    }
    if (pageSizeModifier == null) {
      throw new IllegalArgumentException(
          "Page size modification is not supported for this paginator. "
              + "Set the page size when creating the paginator (e.g., searchPaged(artist, pageSize)).");
    }
    return new Paginator<>(pageSizeModifier.apply(pageSize), pageSizeModifier, maxItems);
  }

  /**
   * Returns an iterable that fetches up to a maximum number of items across all pages.
   * Can be used with enhanced for-each loops.
   *
   * <p>Example:
   * <pre>{@code
   * for (Item item : paginator.take(100)) {
   *   // process up to 100 items total
   * }
   * }</pre>
   *
   * @param maxItems maximum number of items to return across all pages
   * @return an iterable over up to maxItems items
   */
  public Iterable<T> take(int maxItems) {
    return PaginationHelper.iterableItems(pageFetcher, maxItems);
  }

  /**
   * Returns a stream that automatically fetches and streams all pages of results.
   * If a max items limit was set when creating the paginator, it will be respected.
   *
   * @return a stream over all items (or up to maxItems if set)
   */
  public Stream<T> stream() {
    if (maxItems != null) {
      Iterable<T> iterable = PaginationHelper.iterableItems(pageFetcher, maxItems);
      return java.util.stream.StreamSupport.stream(iterable.spliterator(), false);
    }
    return PaginationHelper.streamAll(pageFetcher);
  }

  /**
   * Collects all items from all pages into a list.
   * If a max items limit was set when creating the paginator, it will be respected.
   *
   * @return a list containing all items (or up to maxItems if set)
   */
  public List<T> toList() {
    if (maxItems != null) {
      return PaginationHelper.collectItems(pageFetcher, maxItems);
    }
    return PaginationHelper.collectAll(pageFetcher);
  }

  /**
   * Collects up to a maximum number of items across all pages.
   *
   * @param maxItems maximum number of items to collect across all pages
   * @return a list containing up to maxItems items
   */
  public List<T> toList(int maxItems) {
    return PaginationHelper.collectItems(pageFetcher, maxItems);
  }
}

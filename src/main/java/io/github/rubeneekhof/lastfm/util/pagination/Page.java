package io.github.rubeneekhof.lastfm.util.pagination;

import java.util.List;
import java.util.Optional;

/**
 * Represents a single page of paginated results with metadata.
 *
 * @param <T> the type of items in the page
 */
public record Page<T>(
    /** The items on this page */
    List<T> items,
    /** The current page number (1-based) */
    int page,
    /** The number of items per page */
    int pageSize,
    /** Total number of pages (if available) */
    Optional<Integer> totalPages,
    /** Total number of items across all pages (if available) */
    Optional<Integer> totalItems) {

  /**
   * Creates a page with all metadata available.
   *
   * @param items the items on this page
   * @param page the current page number (1-based)
   * @param pageSize the number of items per page
   * @param totalPages total number of pages
   * @param totalItems total number of items
   */
  public Page {
    if (page < 1) {
      throw new IllegalArgumentException("Page must be >= 1");
    }
    if (pageSize < 1) {
      throw new IllegalArgumentException("Page size must be >= 1");
    }
    if (totalPages.isPresent() && totalPages.get() < 0) {
      throw new IllegalArgumentException("Total pages must be >= 0");
    }
    if (totalItems.isPresent() && totalItems.get() < 0) {
      throw new IllegalArgumentException("Total items must be >= 0");
    }
  }

  /**
   * Creates a page without total metadata (for endpoints that don't provide it).
   *
   * @param items the items on this page
   * @param page the current page number (1-based)
   * @param pageSize the number of items per page
   */
  public Page(List<T> items, int page, int pageSize) {
    this(items, page, pageSize, Optional.empty(), Optional.empty());
  }

  /**
   * Checks if there is a next page available.
   *
   * @return true if there is a next page, false otherwise
   */
  public boolean hasNext() {
    return totalPages
        .map(total -> page < total)
        .orElse(!items.isEmpty()); // If no total info, assume there's more if current page has items
  }

  /**
   * Checks if there is a previous page available.
   *
   * @return true if there is a previous page, false otherwise
   */
  public boolean hasPrevious() {
    return page > 1;
  }

  /**
   * Gets the next page number, if available.
   *
   * @return the next page number, or empty if this is the last page
   */
  public Optional<Integer> nextPage() {
    return hasNext() ? Optional.of(page + 1) : Optional.empty();
  }

  /**
   * Gets the previous page number, if available.
   *
   * @return the previous page number, or empty if this is the first page
   */
  public Optional<Integer> previousPage() {
    return hasPrevious() ? Optional.of(page - 1) : Optional.empty();
  }

  /**
   * Checks if this page is empty.
   *
   * @return true if the page has no items
   */
  public boolean isEmpty() {
    return items == null || items.isEmpty();
  }

  /**
   * Gets the number of items on this page.
   *
   * @return the number of items
   */
  public int size() {
    return items == null ? 0 : items.size();
  }
}

package io.github.rubeneekhof.lastfm.util.pagination;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Iterator that automatically fetches and iterates over all pages of results.
 *
 * @param <T> the type of items in the pages
 */
public class PaginationIterator<T> implements Iterator<T> {

  private final PageFetcher<T> pageFetcher;
  private final Integer maxPages;
  private final Integer maxItems;

  private Page<T> currentPage;
  private int currentPageNumber = 1;
  private int currentItemIndex = 0;
  private int totalItemsProcessed = 0;
  private boolean initialized = false;

  /**
   * Creates a pagination iterator that fetches all pages.
   *
   * @param pageFetcher the function to fetch pages
   */
  public PaginationIterator(PageFetcher<T> pageFetcher) {
    this(pageFetcher, null, null);
  }

  /**
   * Creates a pagination iterator with optional limits.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxPages maximum number of pages to fetch (null for unlimited)
   * @param maxItems maximum number of items to return (null for unlimited)
   */
  public PaginationIterator(PageFetcher<T> pageFetcher, Integer maxPages, Integer maxItems) {
    if (pageFetcher == null) {
      throw new IllegalArgumentException("Page fetcher must not be null");
    }
    if (maxPages != null && maxPages < 1) {
      throw new IllegalArgumentException("Max pages must be >= 1");
    }
    if (maxItems != null && maxItems < 1) {
      throw new IllegalArgumentException("Max items must be >= 1");
    }
    this.pageFetcher = pageFetcher;
    this.maxPages = maxPages;
    this.maxItems = maxItems;
  }

  private void ensureInitialized() {
    if (!initialized) {
      currentPage = pageFetcher.fetch(currentPageNumber);
      initialized = true;
    }
  }

  @Override
  public boolean hasNext() {
    ensureInitialized();

    // Check if we've hit max items limit
    if (maxItems != null && totalItemsProcessed >= maxItems) {
      return false;
    }

    // Check if we have more items on current page
    if (currentItemIndex < currentPage.size()) {
      return true;
    }

    // Check if we've hit max pages limit
    if (maxPages != null && currentPageNumber >= maxPages) {
      return false;
    }

    // Try to fetch next page
    if (!currentPage.hasNext()) {
      return false;
    }

    Optional<Integer> nextPageNum = currentPage.nextPage();
    if (nextPageNum.isEmpty()) {
      return false;
    }

    currentPage = pageFetcher.fetch(nextPageNum.get());
    currentPageNumber = nextPageNum.get();
    currentItemIndex = 0;

    // If next page is empty, we're done
    return !currentPage.isEmpty();
  }

  @Override
  public T next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No more items available");
    }

    T item = currentPage.items().get(currentItemIndex);
    currentItemIndex++;
    totalItemsProcessed++;

    return item;
  }
}

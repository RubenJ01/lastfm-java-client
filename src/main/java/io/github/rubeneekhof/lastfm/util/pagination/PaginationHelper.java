package io.github.rubeneekhof.lastfm.util.pagination;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class for working with paginated results. Provides helper methods for common pagination
 * patterns.
 */
public final class PaginationHelper {

  private PaginationHelper() {
    // Utility class
  }

  /**
   * Creates an iterator that automatically fetches and iterates over all pages.
   *
   * @param pageFetcher the function to fetch pages
   * @param <T> the type of items
   * @return an iterator over all items across all pages
   */
  public static <T> Iterator<T> iterateAll(PageFetcher<T> pageFetcher) {
    return new PaginationIterator<>(pageFetcher);
  }

  /**
   * Creates an iterable that automatically fetches and iterates over all pages.
   * Can be used with enhanced for-each loops.
   *
   * @param pageFetcher the function to fetch pages
   * @param <T> the type of items
   * @return an iterable over all items across all pages
   */
  public static <T> Iterable<T> iterableAll(PageFetcher<T> pageFetcher) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterateAll(pageFetcher);
      }
    };
  }

  /**
   * Creates an iterator with a maximum number of pages limit.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxPages maximum number of pages to fetch
   * @param <T> the type of items
   * @return an iterator over items from up to maxPages pages
   */
  public static <T> Iterator<T> iteratePages(PageFetcher<T> pageFetcher, int maxPages) {
    return new PaginationIterator<>(pageFetcher, maxPages, null);
  }

  /**
   * Creates an iterable with a maximum number of pages limit.
   * Can be used with enhanced for-each loops.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxPages maximum number of pages to fetch
   * @param <T> the type of items
   * @return an iterable over items from up to maxPages pages
   */
  public static <T> Iterable<T> iterablePages(PageFetcher<T> pageFetcher, int maxPages) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iteratePages(pageFetcher, maxPages);
      }
    };
  }

  /**
   * Creates an iterator with a maximum number of items limit.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxItems maximum number of items to return
   * @param <T> the type of items
   * @return an iterator over up to maxItems items
   */
  public static <T> Iterator<T> iterateItems(PageFetcher<T> pageFetcher, int maxItems) {
    return new PaginationIterator<>(pageFetcher, null, maxItems);
  }

  /**
   * Creates an iterable with a maximum number of items limit.
   * Can be used with enhanced for-each loops.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxItems maximum number of items to return
   * @param <T> the type of items
   * @return an iterable over up to maxItems items
   */
  public static <T> Iterable<T> iterableItems(PageFetcher<T> pageFetcher, int maxItems) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterateItems(pageFetcher, maxItems);
      }
    };
  }

  /**
   * Creates a stream that automatically fetches and streams all pages.
   *
   * @param pageFetcher the function to fetch pages
   * @param <T> the type of items
   * @return a stream over all items across all pages
   */
  public static <T> Stream<T> streamAll(PageFetcher<T> pageFetcher) {
    Iterator<T> iterator = iterateAll(pageFetcher);
    Iterable<T> iterable = new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterator;
      }
    };
    return StreamSupport.stream(iterable.spliterator(), false);
  }

  /**
   * Collects all items from all pages into a list.
   *
   * @param pageFetcher the function to fetch pages
   * @param <T> the type of items
   * @return a list containing all items from all pages
   */
  public static <T> List<T> collectAll(PageFetcher<T> pageFetcher) {
    List<T> result = new ArrayList<>();
    Iterator<T> iterator = iterateAll(pageFetcher);
    iterator.forEachRemaining(result::add);
    return result;
  }

  /**
   * Collects items from all pages up to a maximum number of items.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxItems maximum number of items to collect
   * @param <T> the type of items
   * @return a list containing up to maxItems items
   */
  public static <T> List<T> collectItems(PageFetcher<T> pageFetcher, int maxItems) {
    List<T> result = new ArrayList<>();
    Iterator<T> iterator = iterateItems(pageFetcher, maxItems);
    iterator.forEachRemaining(result::add);
    return result;
  }

  /**
   * Collects items from a maximum number of pages.
   *
   * @param pageFetcher the function to fetch pages
   * @param maxPages maximum number of pages to fetch
   * @param <T> the type of items
   * @return a list containing all items from up to maxPages pages
   */
  public static <T> List<T> collectPages(PageFetcher<T> pageFetcher, int maxPages) {
    List<T> result = new ArrayList<>();
    Iterator<T> iterator = iteratePages(pageFetcher, maxPages);
    iterator.forEachRemaining(result::add);
    return result;
  }
}

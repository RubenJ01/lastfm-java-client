package io.github.rubeneekhof.lastfm.application.artist;

import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.model.artist.ArtistSearchResult;
import io.github.rubeneekhof.lastfm.util.pagination.Page;
import io.github.rubeneekhof.lastfm.util.pagination.PaginationHelper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Provides pagination support for artist search results. Wraps the search functionality with
 * convenient pagination helpers.
 */
public final class ArtistSearchPagination {

  private final ArtistService artistService;
  private final String query;
  private final int defaultPageSize;

  ArtistSearchPagination(ArtistService artistService, String query, int defaultPageSize) {
    this.artistService = artistService;
    this.query = query;
    this.defaultPageSize = defaultPageSize;
  }

  /**
   * Fetches a specific page of search results.
   *
   * @param page the page number (1-based)
   * @return a page of artists
   */
  public Page<Artist> page(int page) {
    ArtistSearchResult result = artistService.search(query, defaultPageSize, page);
    return toPage(result, page);
  }

  /**
   * Returns an iterable that automatically fetches and iterates over all pages of search results.
   * Can be used with enhanced for-each loops.
   *
   * <p>Example:
   * <pre>{@code
   * for (Artist artist : client.artists().searchPaged("Low").iterateAll()) {
   *   // process artist
   * }
   * }</pre>
   *
   * @return an iterable over all artists
   */
  public Iterable<Artist> iterateAll() {
    return PaginationHelper.iterableAll(this::page);
  }

  /**
   * Returns an iterable that fetches up to a maximum number of items.
   * Can be used with enhanced for-each loops.
   *
   * <p>Example:
   * <pre>{@code
   * for (Artist artist : client.artists().searchPaged("Low").limit(100)) {
   *   // process up to 100 artists
   * }
   * }</pre>
   *
   * @param maxItems maximum number of items to return
   * @return an iterable over up to maxItems artists
   */
  public Iterable<Artist> limit(int maxItems) {
    return PaginationHelper.iterableItems(this::page, maxItems);
  }

  /**
   * Returns a stream that automatically fetches and streams all pages of search results.
   *
   * @return a stream over all artists
   */
  public Stream<Artist> stream() {
    return PaginationHelper.streamAll(this::page);
  }

  /**
   * Collects all artists from all pages into a list.
   *
   * @return a list containing all artists
   */
  public List<Artist> toList() {
    return PaginationHelper.collectAll(this::page);
  }

  /**
   * Collects up to a maximum number of artists.
   *
   * @param maxItems maximum number of artists to collect
   * @return a list containing up to maxItems artists
   */
  public List<Artist> toList(int maxItems) {
    return PaginationHelper.collectItems(this::page, maxItems);
  }

  private Page<Artist> toPage(ArtistSearchResult result, int page) {
    int totalPages = result.itemsPerPage() > 0
        ? (int) Math.ceil((double) result.totalResults() / result.itemsPerPage())
        : 0;

    return new Page<>(
        result.artists(),
        page,
        result.itemsPerPage(),
        Optional.of(totalPages),
        Optional.of(result.totalResults()));
  }
}

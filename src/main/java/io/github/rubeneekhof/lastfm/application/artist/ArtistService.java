package io.github.rubeneekhof.lastfm.application.artist;

import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.model.artist.ArtistSearchResult;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;
import io.github.rubeneekhof.lastfm.util.pagination.Page;
import io.github.rubeneekhof.lastfm.util.pagination.PageFetcher;
import io.github.rubeneekhof.lastfm.util.pagination.Paginator;
import io.github.rubeneekhof.lastfm.util.pagination.PaginationUtils;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ArtistService {

  private static final boolean DEFAULT_AUTOCORRECT = true;
  private static final int DEFAULT_LIMIT = 5;
  private static final int API_MAX_PAGE_SIZE = 50; // Maximum items per page allowed by Last.fm API

  private final ArtistGateway gateway;

  public ArtistService(ArtistGateway gateway) {
    this.gateway = gateway;
  }

  public Artist getInfo(String artist) {
    validateArtistName(artist);
    return getInfo(ArtistGetInfoRequest.artist(artist).build());
  }

  public Artist getInfo(String artist, String lang) {
    validateArtistName(artist);
    return getInfo(ArtistGetInfoRequest.artist(artist).lang(lang).build());
  }

  public Artist getInfo(String artist, boolean autocorrect) {
    validateArtistName(artist);
    return getInfo(ArtistGetInfoRequest.artist(artist).autocorrect(autocorrect).build());
  }

  public Artist getInfo(ArtistGetInfoRequest request) {
    return gateway.getInfo(
        request.artist(),
        request.mbid(),
        request.lang(),
        request.autocorrect(),
        request.username());
  }

  public List<Artist> getSimilar(String artistName) {
    validateArtistName(artistName);
    return getSimilar(
        ArtistGetSimilarRequest.artist(artistName)
            .autocorrect(DEFAULT_AUTOCORRECT)
            .limit(DEFAULT_LIMIT)
            .build());
  }

  public List<Artist> getSimilar(String artistName, int limit) {
    validateArtistName(artistName);
    validateLimit(limit);
    return getSimilar(
        ArtistGetSimilarRequest.artist(artistName)
            .autocorrect(DEFAULT_AUTOCORRECT)
            .limit(limit)
            .build());
  }

  public List<Artist> getSimilar(String artistName, boolean autocorrect, int limit) {
    validateArtistName(artistName);
    validateLimit(limit);
    return getSimilar(
        ArtistGetSimilarRequest.artist(artistName).autocorrect(autocorrect).limit(limit).build());
  }

  public List<Artist> getSimilar(ArtistGetSimilarRequest request) {
    return gateway.getSimilar(
        request.artist(), request.mbid(), request.autocorrect(), request.limit());
  }

  public Optional<Artist> getCorrection(String artist) {
    validateArtistName(artist);
    return gateway.getCorrection(artist);
  }

  private void validateArtistName(String artistName) {
    if (artistName == null || artistName.isBlank()) {
      throw new IllegalArgumentException("Artist name must not be blank");
    }
  }

  private void validateLimit(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("Limit must be greater than zero");
    }
  }

  public ArtistSearchResult search(String artist) {
    validateArtistName(artist);
    return search(ArtistSearchRequest.artist(artist).build());
  }

  public ArtistSearchResult search(String artist, int limit) {
    validateArtistName(artist);
    validateLimit(limit);
    return search(ArtistSearchRequest.artist(artist).limit(limit).build());
  }

  public ArtistSearchResult search(String artist, int limit, int page) {
    validateArtistName(artist);
    validateLimit(limit);
    validatePage(page);
    return search(ArtistSearchRequest.artist(artist).limit(limit).page(page).build());
  }

  public ArtistSearchResult search(ArtistSearchRequest request) {
    return gateway.search(request.artist(), request.limit(), request.page());
  }

  /**
   * Creates a pagination helper for searching artists. Provides convenient methods for iterating
   * over all pages of search results.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * // Iterate over all artists
   * for (Artist artist : client.artists().searchPaged("Low").iterateAll()) {
   *   // process artist
   * }
   *
   * // Get first 100 artists
   * List<Artist> artists = client.artists().searchPaged("Low").toList(100);
   * }</pre>
   *
   * @param artist the artist name to search for
   * @return a pagination wrapper for the search results
   */
  public Paginator<Artist> searchPaged(String artist) {
    return searchPaged(artist, DEFAULT_LIMIT);
  }

  /**
   * Creates a pagination helper for searching artists with a custom page size.
   *
   * @param artist the artist name to search for
   * @param pageSize the number of results per page
   * @return a pagination wrapper for the search results
   */
  public Paginator<Artist> searchPaged(String artist, int pageSize) {
    return searchPaged(ArtistSearchRequest.artist(artist).limit(pageSize).build());
  }

  /**
   * Creates a pagination helper for searching artists using the builder pattern.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * // Using regular request (for backward compatibility)
   * Paginator<Artist> wrapper = client.artists().searchPaged(
   *     ArtistSearchRequest.artist("Low")
   *         .limit(50)
   *         .build()
   * );
   *
   * // Using paginated request (recommended)
   * Paginator<Artist> wrapper = client.artists().searchPaged(
   *     ArtistSearchPagedRequest.artist("Low")
   *         .pageSize(50)
   *         .maxItems(100)
   *         .build()
   * );
   * }</pre>
   *
   * @param request the search request (page parameter is ignored, pagination is handled internally)
   * @return a pagination wrapper for the search results
   */
  public Paginator<Artist> searchPaged(ArtistSearchRequest request) {
    String artist = request.artist();
    int pageSize = request.limit() != null ? request.limit() : DEFAULT_LIMIT;
    
    PageFetcher<Artist> pageFetcher = page -> {
      ArtistSearchRequest pageRequest = ArtistSearchRequest.artist(artist)
          .limit(pageSize)
          .page(page)
          .build();
      ArtistSearchResult result = search(pageRequest);
      int totalPages = result.itemsPerPage() > 0
          ? (int) Math.ceil((double) result.totalResults() / result.itemsPerPage())
          : 0;
      return new Page<>(
          result.artists(),
          page,
          result.itemsPerPage(),
          Optional.of(totalPages),
          Optional.of(result.totalResults()));
    };
    
    Function<Integer, PageFetcher<Artist>> pageSizeModifier = newPageSize -> page -> {
      ArtistSearchRequest pageRequest = ArtistSearchRequest.artist(artist)
          .limit(newPageSize)
          .page(page)
          .build();
      ArtistSearchResult result = search(pageRequest);
      int totalPages = result.itemsPerPage() > 0
          ? (int) Math.ceil((double) result.totalResults() / result.itemsPerPage())
          : 0;
      return new Page<>(
          result.artists(),
          page,
          result.itemsPerPage(),
          Optional.of(totalPages),
          Optional.of(result.totalResults()));
    };
    
    return new Paginator<>(pageFetcher, pageSizeModifier);
  }

  /**
   * Creates a pagination helper for searching artists using the paginated request builder.
   * This is the recommended way to create paginators as it clearly separates page size from max items.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * // Fetch 50 items per page, but only return first 100 items total
   * Paginator<Artist> wrapper = client.artists().searchPaged(
   *     ArtistSearchPagedRequest.artist("Low")
   *         .pageSize(50)
   *         .maxItems(100)
   *         .build()
   * );
   *
   * // Just limit total items to 5
   * Paginator<Artist> wrapper = client.artists().searchPaged(
   *     ArtistSearchPagedRequest.artist("Low")
   *         .maxItems(5)
   *         .build()
   * );
   * }</pre>
   *
   * @param request the paginated search request
   * @return a pagination wrapper for the search results
   */
  public Paginator<Artist> searchPaged(ArtistSearchPagedRequest request) {
    String artist = request.artist();
    Integer requestedPageSize = request.pageSize();
    Integer maxItems = request.maxItems();
    
    // Optimize page size to minimize API calls when maxItems is set
    int optimalPageSize =
        PaginationUtils.optimizePageSize(requestedPageSize, maxItems, DEFAULT_LIMIT, API_MAX_PAGE_SIZE);
    
    PageFetcher<Artist> pageFetcher = page -> {
      ArtistSearchRequest pageRequest = ArtistSearchRequest.artist(artist)
          .limit(optimalPageSize)
          .page(page)
          .build();
      ArtistSearchResult result = search(pageRequest);
      int totalPages = result.itemsPerPage() > 0
          ? (int) Math.ceil((double) result.totalResults() / result.itemsPerPage())
          : 0;
      return new Page<>(
          result.artists(),
          page,
          result.itemsPerPage(),
          Optional.of(totalPages),
          Optional.of(result.totalResults()));
    };
    
    Function<Integer, PageFetcher<Artist>> pageSizeModifier = newPageSize -> page -> {
      ArtistSearchRequest pageRequest = ArtistSearchRequest.artist(artist)
          .limit(newPageSize)
          .page(page)
          .build();
      ArtistSearchResult result = search(pageRequest);
      int totalPages = result.itemsPerPage() > 0
          ? (int) Math.ceil((double) result.totalResults() / result.itemsPerPage())
          : 0;
      return new Page<>(
          result.artists(),
          page,
          result.itemsPerPage(),
          Optional.of(totalPages),
          Optional.of(result.totalResults()));
    };
    
    return new Paginator<>(pageFetcher, pageSizeModifier, maxItems);
  }


  private void validatePage(int page) {
    if (page <= 0) {
      throw new IllegalArgumentException("Page must be greater than zero");
    }
  }
}

package io.github.rubeneekhof.lastfm.application.artist;

import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.model.artist.ArtistSearchResult;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;
import java.util.List;
import java.util.Optional;

public class ArtistService {

  private static final boolean DEFAULT_AUTOCORRECT = true;
  private static final int DEFAULT_LIMIT = 5;

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
   * for (Artist artist : artistService.searchPaged("Low").iterateAll()) {
   *   // process artist
   * }
   *
   * // Get first 100 artists
   * List<Artist> artists = artistService.searchPaged("Low").toList(100);
   * }</pre>
   *
   * @param artist the artist name to search for
   * @return a pagination helper for the search results
   */
  public ArtistSearchPagination searchPaged(String artist) {
    validateArtistName(artist);
    return new ArtistSearchPagination(this, artist, DEFAULT_LIMIT);
  }

  /**
   * Creates a pagination helper for searching artists with a custom page size.
   *
   * @param artist the artist name to search for
   * @param pageSize the number of results per page
   * @return a pagination helper for the search results
   */
  public ArtistSearchPagination searchPaged(String artist, int pageSize) {
    validateArtistName(artist);
    validateLimit(pageSize);
    return new ArtistSearchPagination(this, artist, pageSize);
  }

  private void validatePage(int page) {
    if (page <= 0) {
      throw new IllegalArgumentException("Page must be greater than zero");
    }
  }
}

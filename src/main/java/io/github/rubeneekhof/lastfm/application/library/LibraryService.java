package io.github.rubeneekhof.lastfm.application.library;

import io.github.rubeneekhof.lastfm.domain.model.LibraryArtist;
import io.github.rubeneekhof.lastfm.domain.port.LibraryGateway;
import java.util.List;

public class LibraryService {

  private final LibraryGateway gateway;

  public LibraryService(LibraryGateway gateway) {
    this.gateway = gateway;
  }

  public List<LibraryArtist> getArtists(String user) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    return gateway.getArtists(user, null, null);
  }

  public List<LibraryArtist> getArtists(String user, int page) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    validatePage(page);
    return gateway.getArtists(user, page, null);
  }

  public List<LibraryArtist> getArtists(String user, int page, int limit) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    validatePage(page);
    validateLimit(limit);
    return gateway.getArtists(user, page, limit);
  }

  public List<LibraryArtist> getArtists(LibraryGetArtistsRequest request) {
    return gateway.getArtists(request.user(), request.page(), request.limit());
  }

  private void validatePage(int page) {
    if (page <= 0) {
      throw new IllegalArgumentException("Page must be greater than zero");
    }
  }

  private void validateLimit(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("Limit must be greater than zero");
    }
  }
}

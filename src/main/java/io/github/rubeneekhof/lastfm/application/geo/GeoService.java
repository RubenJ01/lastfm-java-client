package io.github.rubeneekhof.lastfm.application.geo;

import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.port.GeoGateway;
import java.util.List;

public class GeoService {

  private static final int DEFAULT_LIMIT = 50;

  private final GeoGateway gateway;

  public GeoService(GeoGateway gateway) {
    this.gateway = gateway;
  }

  public List<Artist> getTopArtists(String country) {
    return getTopArtists(GeoGetTopArtistsRequest.builder().country(country).build());
  }

  public List<Artist> getTopArtists(String country, int page) {
    validatePage(page);
    return getTopArtists(GeoGetTopArtistsRequest.builder().country(country).page(page).build());
  }

  public List<Artist> getTopArtists(String country, int page, int limit) {
    validatePage(page);
    validateLimit(limit);
    return getTopArtists(
        GeoGetTopArtistsRequest.builder().country(country).page(page).limit(limit).build());
  }

  public List<Artist> getTopArtists(GeoGetTopArtistsRequest request) {
    return gateway.getTopArtists(request.country(), request.page(), request.limit());
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

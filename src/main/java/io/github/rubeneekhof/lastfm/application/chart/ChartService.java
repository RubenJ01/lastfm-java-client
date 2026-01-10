package io.github.rubeneekhof.lastfm.application.chart;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ChartGateway;

import java.util.List;

public class ChartService {

    private static final int DEFAULT_LIMIT = 50;

    private final ChartGateway gateway;

    public ChartService(ChartGateway gateway) {
        this.gateway = gateway;
    }

    public List<Artist> getTopArtists() {
        return getTopArtists(ChartGetTopArtistsRequest.defaults());
    }

    public List<Artist> getTopArtists(int page) {
        validatePage(page);
        return getTopArtists(ChartGetTopArtistsRequest.builder().page(page).build());
    }

    public List<Artist> getTopArtists(int page, int limit) {
        validatePage(page);
        validateLimit(limit);
        return getTopArtists(ChartGetTopArtistsRequest.builder().page(page).limit(limit).build());
    }

    public List<Artist> getTopArtists(ChartGetTopArtistsRequest request) {
        return gateway.getTopArtists(request.page(), request.limit());
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

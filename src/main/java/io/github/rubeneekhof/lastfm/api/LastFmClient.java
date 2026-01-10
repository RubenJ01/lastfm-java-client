package io.github.rubeneekhof.lastfm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.application.artist.ArtistService;
import io.github.rubeneekhof.lastfm.application.chart.ChartService;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.ArtistGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.ChartGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

public class LastFmClient {

    private final ArtistService artistService;
    private final ChartService chartService;

    private LastFmClient(ArtistService artistService, ChartService chartService) {
        this.artistService = artistService;
        this.chartService = chartService;
    }

    public static LastFmClient create(String apiKey) {
        ObjectMapper mapper = new ObjectMapper();
        HttpExecutor http = new HttpExecutor(apiKey, mapper);
        
        ArtistGatewayImpl artistGateway = new ArtistGatewayImpl(http, mapper);
        ArtistService artistService = new ArtistService(artistGateway);

        ChartGatewayImpl chartGateway = new ChartGatewayImpl(http, mapper);
        ChartService chartService = new ChartService(chartGateway);

        return new LastFmClient(artistService, chartService);
    }

    public ArtistService artists() {
        return artistService;
    }

    public ChartService charts() {
        return chartService;
    }
}

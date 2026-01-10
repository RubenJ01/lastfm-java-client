package io.github.rubeneekhof.lastfm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.application.ArtistService;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.ArtistGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

public class LastFmClient {

    private final ArtistService artistService;

    private LastFmClient(ArtistService artistService) {
        this.artistService = artistService;
    }

    public static LastFmClient create(String apiKey) {
        ObjectMapper mapper = new ObjectMapper();
        HttpExecutor http = new HttpExecutor(apiKey, mapper);
        ArtistGatewayImpl artistGateway = new ArtistGatewayImpl(http, mapper);

        ArtistService artistService = new ArtistService(artistGateway);
        return new LastFmClient(artistService);
    }

    public ArtistService artists() {
        return artistService;
    }
}

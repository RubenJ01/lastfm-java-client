package io.github.rubeneekhof.lastfm.api;

import io.github.rubeneekhof.lastfm.application.ArtistService;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.ArtistGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

public class LastFmClient {

    private final ArtistService artistService;

    private LastFmClient(ArtistService artistService) {
        this.artistService = artistService;
    }

    public static LastFmClient create(String apiKey) {
        HttpExecutor http = new HttpExecutor(apiKey);
        ArtistGatewayImpl artistGateway = new ArtistGatewayImpl(http);

        ArtistService artistService = new ArtistService(artistGateway);
        return new LastFmClient(artistService);
    }

    public ArtistService artists() {
        return artistService;
    }
}

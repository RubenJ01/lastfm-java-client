package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

import java.util.List;
import java.util.Map;

public class ArtistGatewayImpl implements ArtistGateway {

    private final HttpExecutor http;
    private final ObjectMapper mapper;

    public ArtistGatewayImpl(HttpExecutor http) {
        this.http = http;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Artist getInfo(String artistName) {
        GetInfoResponse response = getAndParse(http, mapper,
                "artist.getinfo",
                Map.of("artist", artistName),
                GetInfoResponse.class
        );
        return ArtistMapper.toDomain(response.artist);
    }

    @Override
    public List<Artist> getSimilar(String artistName, int limit) {
        GetSimilarResponse response = getAndParse(
                http, mapper,
                "artist.getsimilar",
                Map.of("artist", artistName, "limit", String.valueOf(limit)),
                GetSimilarResponse.class
        );

        if (response.similarartists == null || response.similarartists.artist == null) {
            return List.of();
        }

        return response.similarartists.artist.stream()
                .map(ArtistMapper::toDomain)
                .toList();
    }
}

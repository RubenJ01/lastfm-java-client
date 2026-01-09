package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

import java.util.List;
import java.util.Map;

/**
 * Gateway implementation for Last.fm Artist API.
 * Orchestrates HTTP → DTO → Domain mapping.
 * Contains zero mapping logic - all mapping is delegated to ArtistMapper.
 */
public class ArtistGatewayImpl implements ArtistGateway {

    private final HttpExecutor http;
    private final ObjectMapper mapper;

    public ArtistGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
        this.http = http;
        this.mapper = mapper;
    }

    @Override
    public Artist getInfo(String artistName) {
        GetInfoResponse response = getAndParse(
                http,
                mapper,
                "artist.getinfo",
                Map.of("artist", artistName),
                GetInfoResponse.class
        );
        return ArtistMapper.from(response);
    }

    @Override
    public List<Artist> getSimilar(String artistName, boolean autocorrect, int limit) {
        GetSimilarResponse response = getAndParse(
                http,
                mapper,
                "artist.getsimilar",
                Map.of(
                        "artist", artistName,
                        "autocorrect", String.valueOf(autocorrect),
                        "limit", String.valueOf(limit)
                ),
                GetSimilarResponse.class
        );
        return ArtistMapper.from(response);
    }
}

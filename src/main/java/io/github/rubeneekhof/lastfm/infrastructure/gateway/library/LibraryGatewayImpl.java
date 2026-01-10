package io.github.rubeneekhof.lastfm.infrastructure.gateway.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.LibraryArtist;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.LibraryGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.LastFmErrorMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.library.response.GetArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryGatewayImpl implements LibraryGateway {

    private final HttpExecutor http;
    private final ObjectMapper mapper;

    public LibraryGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
        this.http = http;
        this.mapper = mapper;
    }

    @Override
    public List<LibraryArtist> getArtists(String user, Integer page, Integer limit) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("user", user);

            if (page != null) {
                params.put("page", String.valueOf(page));
            }
            if (limit != null) {
                params.put("limit", String.valueOf(limit));
            }

            GetArtistsResponse response = getAndParse(
                    http,
                    mapper,
                    "library.getartists",
                    params,
                    GetArtistsResponse.class
            );
            return LibraryMapper.from(response);
        } catch (LastFmException e) {
            throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
        }
    }
}

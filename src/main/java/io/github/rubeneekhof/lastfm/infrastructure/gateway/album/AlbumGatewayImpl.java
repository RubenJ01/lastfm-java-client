package io.github.rubeneekhof.lastfm.infrastructure.gateway.album;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Album;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.AlbumGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.LastFmErrorMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.album.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

import java.util.HashMap;
import java.util.Map;

public class AlbumGatewayImpl implements AlbumGateway {

    private final HttpExecutor http;
    private final ObjectMapper mapper;

    public AlbumGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
        this.http = http;
        this.mapper = mapper;
    }

    @Override
    public Album getInfo(String artist, String album, String mbid, Boolean autocorrect, String username, String lang) {
        try {
            Map<String, String> params = new HashMap<>();

            if (artist != null) {
                params.put("artist", artist);
            }
            if (album != null) {
                params.put("album", album);
            }
            if (mbid != null) {
                params.put("mbid", mbid);
            }
            if (autocorrect != null) {
                params.put("autocorrect", autocorrect ? "1" : "0");
            }
            if (username != null) {
                params.put("username", username);
            }
            if (lang != null) {
                params.put("lang", lang);
            }

            GetInfoResponse response = getAndParse(
                    http,
                    mapper,
                    "album.getinfo",
                    params,
                    GetInfoResponse.class
            );
            return AlbumMapper.from(response);
        } catch (LastFmException e) {
            throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
        }
    }
}

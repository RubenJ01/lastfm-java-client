package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.LastFmErrorMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Artist getInfo(String artist, String mbid, String lang, Boolean autocorrect, String username) {
        try {
            Map<String, String> params = new HashMap<>();
            
            if (artist != null) {
                params.put("artist", artist);
            }
            if (mbid != null) {
                params.put("mbid", mbid);
            }
            if (lang != null) {
                params.put("lang", lang);
            }
            if (autocorrect != null) {
                params.put("autocorrect", autocorrect ? "1" : "0");
            }
            if (username != null) {
                params.put("username", username);
            }

            GetInfoResponse response = getAndParse(
                    http,
                    mapper,
                    "artist.getinfo",
                    params,
                    GetInfoResponse.class
            );
            return ArtistMapper.from(response);
        } catch (LastFmException e) {
            throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
        }
    }

    @Override
    public List<Artist> getSimilar(String artist, String mbid, Boolean autocorrect, Integer limit) {
        try {
            Map<String, String> params = new HashMap<>();

            if (artist != null) {
                params.put("artist", artist);
            }
            if (mbid != null) {
                params.put("mbid", mbid);
            }
            if (autocorrect != null) {
                params.put("autocorrect", autocorrect ? "1" : "0");
            }
            if (limit != null) {
                params.put("limit", String.valueOf(limit));
            }

            GetSimilarResponse response = getAndParse(
                    http,
                    mapper,
                    "artist.getsimilar",
                    params,
                    GetSimilarResponse.class
            );
            return ArtistMapper.from(response);
        } catch (LastFmException e) {
            throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
        }
    }

    @Override
    public Optional<Artist> getCorrection(String artist) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("artist", artist);

            GetCorrectionResponse response = getAndParse(
                    http,
                    mapper,
                    "artist.getcorrection",
                    params,
                    GetCorrectionResponse.class
            );
            return ArtistMapper.from(response);
        } catch (LastFmException e) {
            throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
        }
    }
}

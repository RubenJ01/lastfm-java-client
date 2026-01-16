package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ArtistGateway;
import io.github.rubeneekhof.lastfm.domain.model.artist.ArtistSearchResult;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.SearchResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArtistGatewayImpl extends BaseGatewayImpl implements ArtistGateway {

  public ArtistGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public Artist getInfo(
      String artist, String mbid, String lang, Boolean autocorrect, String username) {
    Map<String, String> params =
        ParameterBuilder.create()
            .put("artist", artist)
            .put("mbid", mbid)
            .put("lang", lang)
            .put("autocorrect", autocorrect)
            .put("username", username)
            .build();

    GetInfoResponse response =
        executeWithErrorHandling("artist.getinfo", params, GetInfoResponse.class);
    return ArtistMapper.from(response);
  }

  @Override
  public List<Artist> getSimilar(String artist, String mbid, Boolean autocorrect, Integer limit) {
    Map<String, String> params =
        ParameterBuilder.create()
            .put("artist", artist)
            .put("mbid", mbid)
            .put("autocorrect", autocorrect)
            .put("limit", limit)
            .build();

    GetSimilarResponse response =
        executeWithErrorHandling("artist.getsimilar", params, GetSimilarResponse.class);
    return ArtistMapper.from(response);
  }

  @Override
  public Optional<Artist> getCorrection(String artist) {
    Map<String, String> params = ParameterBuilder.create().putRequired("artist", artist).build();

    GetCorrectionResponse response =
        executeWithErrorHandling("artist.getcorrection", params, GetCorrectionResponse.class);
    return ArtistMapper.from(response);
  }

  @Override
  public ArtistSearchResult search(String artist, Integer limit, Integer page) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("artist", artist)
            .put("limit", limit)
            .put("page", page)
            .build();

    SearchResponse response =
        executeWithErrorHandling("artist.search", params, SearchResponse.class);
    return ArtistMapper.from(response);
  }
}

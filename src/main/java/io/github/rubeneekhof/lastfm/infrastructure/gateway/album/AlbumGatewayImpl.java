package io.github.rubeneekhof.lastfm.infrastructure.gateway.album;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Album;
import io.github.rubeneekhof.lastfm.domain.port.AlbumGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.album.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.Map;

public class AlbumGatewayImpl extends BaseGatewayImpl implements AlbumGateway {

  public AlbumGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public Album getInfo(
      String artist, String album, String mbid, Boolean autocorrect, String username, String lang) {
    Map<String, String> params =
        ParameterBuilder.create()
            .put("artist", artist)
            .put("album", album)
            .put("mbid", mbid)
            .put("autocorrect", autocorrect)
            .put("username", username)
            .put("lang", lang)
            .build();

    GetInfoResponse response =
        executeWithErrorHandling("album.getinfo", params, GetInfoResponse.class);
    return AlbumMapper.from(response);
  }
}

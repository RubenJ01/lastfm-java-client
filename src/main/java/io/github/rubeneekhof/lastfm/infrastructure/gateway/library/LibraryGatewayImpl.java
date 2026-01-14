package io.github.rubeneekhof.lastfm.infrastructure.gateway.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.LibraryArtist;
import io.github.rubeneekhof.lastfm.domain.port.LibraryGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.library.response.GetArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.List;
import java.util.Map;

public class LibraryGatewayImpl extends BaseGatewayImpl implements LibraryGateway {

  public LibraryGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public List<LibraryArtist> getArtists(String user, Integer page, Integer limit) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("user", user)
            .put("page", page)
            .put("limit", limit)
            .build();

    GetArtistsResponse response =
        executeWithErrorHandling("library.getartists", params, GetArtistsResponse.class);
    return LibraryMapper.from(response);
  }
}

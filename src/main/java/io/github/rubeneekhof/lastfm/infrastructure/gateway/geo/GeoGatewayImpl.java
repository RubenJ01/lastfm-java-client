package io.github.rubeneekhof.lastfm.infrastructure.gateway.geo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.GeoGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.geo.response.GetTopArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.List;
import java.util.Map;

public class GeoGatewayImpl extends BaseGatewayImpl implements GeoGateway {

  public GeoGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public List<Artist> getTopArtists(String country, Integer page, Integer limit) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("country", country)
            .put("page", page)
            .put("limit", limit)
            .build();

    GetTopArtistsResponse response =
        executeWithErrorHandling("geo.gettopartists", params, GetTopArtistsResponse.class);
    return GeoMapper.from(response);
  }
}

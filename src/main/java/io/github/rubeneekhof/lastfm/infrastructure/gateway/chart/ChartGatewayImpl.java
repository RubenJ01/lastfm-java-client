package io.github.rubeneekhof.lastfm.infrastructure.gateway.chart;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.port.ChartGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.response.GetTopArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.List;
import java.util.Map;

public class ChartGatewayImpl extends BaseGatewayImpl implements ChartGateway {

  public ChartGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public List<Artist> getTopArtists(Integer page, Integer limit) {
    Map<String, String> params =
        ParameterBuilder.create().put("page", page).put("limit", limit).build();

    GetTopArtistsResponse response =
        executeWithErrorHandling("chart.gettopartists", params, GetTopArtistsResponse.class);
    return ChartMapper.from(response);
  }
}

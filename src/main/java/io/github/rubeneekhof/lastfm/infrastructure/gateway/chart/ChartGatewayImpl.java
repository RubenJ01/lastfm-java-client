package io.github.rubeneekhof.lastfm.infrastructure.gateway.chart;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.ChartGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.LastFmErrorMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.response.GetTopArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartGatewayImpl implements ChartGateway {

  private final HttpExecutor http;
  private final ObjectMapper mapper;

  public ChartGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    this.http = http;
    this.mapper = mapper;
  }

  @Override
  public List<Artist> getTopArtists(Integer page, Integer limit) {
    try {
      Map<String, String> params = new HashMap<>();

      if (page != null) {
        params.put("page", String.valueOf(page));
      }
      if (limit != null) {
        params.put("limit", String.valueOf(limit));
      }

      GetTopArtistsResponse response =
          getAndParse(http, mapper, "chart.gettopartists", params, GetTopArtistsResponse.class);
      return ChartMapper.from(response);
    } catch (LastFmException e) {
      throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
    }
  }
}

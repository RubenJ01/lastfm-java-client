package io.github.rubeneekhof.lastfm.infrastructure.gateway.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.domain.port.UserGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.domain.model.user.WeeklyAlbumChart;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetWeeklyAlbumChartResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.Map;

public class UserGatewayImpl extends BaseGatewayImpl implements UserGateway {

  public UserGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public User getInfo(String user) {
    Map<String, String> params = ParameterBuilder.create().putRequired("user", user).build();

    GetInfoResponse response =
        executeWithErrorHandling("user.getInfo", params, GetInfoResponse.class);
    return UserMapper.from(response);
  }

  @Override
  public WeeklyAlbumChart getWeeklyAlbumChart(String user, Integer limit, Long from, Long to) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("user", user)
            .put("limit", limit)
            .put("from", from != null ? String.valueOf(from) : null)
            .put("to", to != null ? String.valueOf(to) : null)
            .build();

    GetWeeklyAlbumChartResponse response =
        executeWithErrorHandling(
            "user.getweeklyalbumchart", params, GetWeeklyAlbumChartResponse.class);
    return UserMapper.from(response);
  }
}

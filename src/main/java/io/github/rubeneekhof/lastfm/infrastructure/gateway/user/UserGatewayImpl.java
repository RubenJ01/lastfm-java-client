package io.github.rubeneekhof.lastfm.infrastructure.gateway.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.user.User;
import io.github.rubeneekhof.lastfm.domain.model.user.FriendsResult;
import io.github.rubeneekhof.lastfm.domain.model.user.LovedTracksResult;
import io.github.rubeneekhof.lastfm.domain.port.UserGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.domain.model.user.WeeklyAlbumChart;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetFriendsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetLovedTracksResponse;
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

  @Override
  public FriendsResult getFriends(String user, Boolean recenttracks, Integer limit, Integer page) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("user", user)
            .put("recenttracks", recenttracks)
            .put("limit", limit)
            .put("page", page)
            .build();

    GetFriendsResponse response =
        executeWithErrorHandling("user.getFriends", params, GetFriendsResponse.class);
    return UserMapper.from(response);
  }

  @Override
  public LovedTracksResult getLovedTracks(String user, Integer limit, Integer page) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("user", user)
            .put("limit", limit)
            .put("page", page)
            .build();

    GetLovedTracksResponse response =
        executeWithErrorHandling("user.getLovedTracks", params, GetLovedTracksResponse.class);
    return UserMapper.from(response);
  }
}

package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.domain.model.user.FriendsResult;
import io.github.rubeneekhof.lastfm.domain.model.user.WeeklyAlbumChart;

public interface UserGateway extends LastFmApiGateway {
  User getInfo(String user);

  WeeklyAlbumChart getWeeklyAlbumChart(String user, Integer limit, Long from, Long to);

  FriendsResult getFriends(String user, Boolean recenttracks, Integer limit, Integer page);
}

package io.github.rubeneekhof.lastfm.infrastructure.gateway.user;

import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.domain.model.user.WeeklyAlbumChart;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetWeeklyAlbumChartResponse;
import java.util.List;
import java.util.Optional;

public class UserMapper extends BaseMapper {

  public static User from(GetInfoResponse response) {
    if (response == null || response.user == null) {
      return null;
    }

    GetInfoResponse.UserData data = response.user;
    return new User(
        data.name,
        data.realname,
        data.url,
        data.country,
        data.gender,
        parseNumber(data.age),
        parseNumber(data.playlists),
        parseLong(data.playcount),
        "1".equals(data.subscriber),
        data.type,
        parseRegistered(data.registered),
        parseNumber(data.bootstrap),
        mapImages(data.image, img -> new User.Image(img.getSize(), img.getUrl())));
  }

  private static long parseRegistered(GetInfoResponse.Registered registered) {
    if (registered == null) {
      return 0;
    }
    if (registered.unixtime != null && !registered.unixtime.isBlank()) {
      return parseLong(registered.unixtime);
    }
    if (registered.text != null && !registered.text.isBlank()) {
      return parseLong(registered.text);
    }
    return 0;
  }

  public static WeeklyAlbumChart from(GetWeeklyAlbumChartResponse response) {
    if (response == null || response.weeklyalbumchart == null) {
      return new WeeklyAlbumChart(null, null, null, List.of());
    }

    GetWeeklyAlbumChartResponse.WeeklyAlbumChart chart = response.weeklyalbumchart;
    String user = chart.attr != null ? chart.attr.user : null;
    Long from = parseTimestamp(chart.attr != null ? chart.attr.from : null);
    Long to = parseTimestamp(chart.attr != null ? chart.attr.to : null);

    List<WeeklyAlbumChart.Album> albums = List.of();
    if (chart.album != null) {
      albums = chart.album.stream().map(UserMapper::fromAlbum).toList();
    }

    return new WeeklyAlbumChart(user, from, to, albums);
  }

  private static WeeklyAlbumChart.Album fromAlbum(
      GetWeeklyAlbumChartResponse.AlbumData data) {
    if (data == null) {
      return null;
    }

    String artist = data.artist != null ? data.artist.text : null;
    int playcount = parseNumber(data.playcount);
    int rank = parseNumber(data.attr != null ? data.attr.rank : null);

    return new WeeklyAlbumChart.Album(
        data.name, artist, data.mbid, data.url, playcount, rank);
  }

  private static Long parseTimestamp(String timestamp) {
    if (timestamp == null || timestamp.isBlank()) {
      return null;
    }
    try {
      return Long.parseLong(timestamp);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}

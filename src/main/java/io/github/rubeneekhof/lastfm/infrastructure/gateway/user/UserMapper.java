package io.github.rubeneekhof.lastfm.infrastructure.gateway.user;

import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.domain.model.user.FriendsResult;
import io.github.rubeneekhof.lastfm.domain.model.user.WeeklyAlbumChart;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetFriendsResponse;
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

  public static FriendsResult from(GetFriendsResponse response) {
    if (response == null || response.friends == null) {
      return new FriendsResult(null, 1, 0, 50, 0, List.of());
    }

    GetFriendsResponse.Friends friends = response.friends;
    GetFriendsResponse.Attr attr = friends.attr;

    String user = attr != null ? attr.user : null;
    int page = parsePage(attr);
    int total = parseTotal(attr);
    int perPage = parsePerPage(attr);
    int totalPages = parseTotalPages(attr);

    List<FriendsResult.Friend> friendList = List.of();
    if (friends.user != null) {
      friendList = friends.user.stream().map(UserMapper::fromFriend).toList();
    }

    return new FriendsResult(user, page, total, perPage, totalPages, friendList);
  }

  private static FriendsResult.Friend fromFriend(GetFriendsResponse.UserData data) {
    if (data == null) {
      return null;
    }

    return new FriendsResult.Friend(
        data.name,
        data.realname,
        data.url,
        data.country,
        parseNumber(data.playlists),
        parseLong(data.playcount),
        "1".equals(data.subscriber),
        data.type,
        parseRegistered(data.registered),
        parseNumber(data.bootstrap),
        mapImages(data.image, img -> new FriendsResult.Friend.Image(img.getSize(), img.getUrl())));
  }

  private static long parseRegistered(GetFriendsResponse.Registered registered) {
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

  private static int parsePage(GetFriendsResponse.Attr attr) {
    if (attr == null || attr.page == null) {
      return 1;
    }
    int parsed = parseNumber(attr.page);
    return parsed > 0 ? parsed : 1;
  }

  private static int parseTotal(GetFriendsResponse.Attr attr) {
    if (attr == null || attr.total == null) {
      return 0;
    }
    return parseNumber(attr.total);
  }

  private static int parsePerPage(GetFriendsResponse.Attr attr) {
    if (attr == null || attr.perPage == null) {
      return 50;
    }
    int parsed = parseNumber(attr.perPage);
    return parsed > 0 ? parsed : 50;
  }

  private static int parseTotalPages(GetFriendsResponse.Attr attr) {
    if (attr == null || attr.totalPages == null) {
      return 0;
    }
    return parseNumber(attr.totalPages);
  }
}

package io.github.rubeneekhof.lastfm.infrastructure.gateway.user;

import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetInfoResponse;

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

}

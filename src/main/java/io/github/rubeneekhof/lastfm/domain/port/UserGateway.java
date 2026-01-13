package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.User;

public interface UserGateway extends LastFmApiGateway {
  User getInfo(String user);
}

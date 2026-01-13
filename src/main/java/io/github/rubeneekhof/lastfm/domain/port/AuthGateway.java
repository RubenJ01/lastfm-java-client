package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Session;

public interface AuthGateway extends LastFmApiGateway {
  String getToken();

  Session getSession(String token);
}

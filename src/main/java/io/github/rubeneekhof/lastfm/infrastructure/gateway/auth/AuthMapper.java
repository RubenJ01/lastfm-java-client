package io.github.rubeneekhof.lastfm.infrastructure.gateway.auth;

import io.github.rubeneekhof.lastfm.domain.model.Session;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.auth.response.GetSessionResponse;

public class AuthMapper {

  public static Session from(GetSessionResponse response) {
    if (response == null || response.session == null) {
      return null;
    }

    GetSessionResponse.Session session = response.session;
    boolean subscriber =
        "1".equals(session.subscriber) || "true".equalsIgnoreCase(session.subscriber);

    return new Session(session.name, session.key, subscriber);
  }
}

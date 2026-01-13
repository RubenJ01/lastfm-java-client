package io.github.rubeneekhof.lastfm.application.user;

import io.github.rubeneekhof.lastfm.domain.model.User;
import io.github.rubeneekhof.lastfm.domain.port.UserGateway;

public class UserService {

  private final UserGateway gateway;

  public UserService(UserGateway gateway) {
    this.gateway = gateway;
  }

  public User getInfo(String user) {
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("User must not be blank");
    }
    return gateway.getInfo(user);
  }
}

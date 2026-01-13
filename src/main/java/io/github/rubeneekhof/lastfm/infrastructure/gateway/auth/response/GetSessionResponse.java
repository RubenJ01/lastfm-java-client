package io.github.rubeneekhof.lastfm.infrastructure.gateway.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSessionResponse {

  public Session session;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Session {
    public String name;
    public String key;
    public String subscriber;
  }
}

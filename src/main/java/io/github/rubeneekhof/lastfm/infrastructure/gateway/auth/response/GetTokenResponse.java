package io.github.rubeneekhof.lastfm.infrastructure.gateway.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTokenResponse {

  public String token;
}

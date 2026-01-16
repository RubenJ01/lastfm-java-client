package io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoveResponse {

  public Lfm lfm;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Lfm {
    @JsonProperty("@status")
    public String status;
  }
}

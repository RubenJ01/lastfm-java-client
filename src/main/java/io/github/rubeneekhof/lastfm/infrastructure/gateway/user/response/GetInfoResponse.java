package io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetInfoResponse {

  public UserData user;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class UserData {
    public String name;
    public String realname;
    public String url;
    public String country;
    public String gender;
    public String age;
    public String playlists;
    public String playcount;
    public String subscriber;
    public String type;
    public String bootstrap;
    public List<Image> image;
    public Registered registered;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Image
      implements BaseImageResponse {
    public String size;

    @JsonProperty("#text")
    public String url;

    @Override
    public String getSize() {
      return size;
    }

    @Override
    public String getUrl() {
      return url;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Registered {
    public String unixtime;

    @JsonProperty("#text")
    public String text;
  }
}

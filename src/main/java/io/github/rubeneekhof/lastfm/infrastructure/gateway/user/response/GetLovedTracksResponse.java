package io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetLovedTracksResponse {

  public LovedTracks lovedtracks;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LovedTracks {
    @JsonProperty("@attr")
    public Attr attr;

    public List<TrackData> track;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Attr {
    public String page;
    public String total;
    public String user;
    public String perPage;
    public String totalPages;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TrackData {
    public String name;
    public String mbid;
    public String url;
    public Artist artist;
    public List<Image> image;
    public Streamable streamable;
    public Date date;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Artist {
    public String name;
    public String mbid;
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Image implements BaseImageResponse {
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
  public static class Streamable {
    @JsonProperty("fulltrack")
    public String fulltrack;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Date {
    @JsonProperty("uts")
    public String uts;

    @JsonProperty("#text")
    public String text;
  }
}

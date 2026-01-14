package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetInfoResponse {

  public ArtistData artist;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ArtistData {
    public String name;
    public String mbid;
    public String url;
    public List<Image> image;
    public String streamable;
    public Stats stats;
    public Similar similar;
    public Tags tags;
    public Bio bio;
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
  public static class Stats {
    public int listeners;
    public int plays;
    public Integer userplaycount;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Similar {
    public List<ArtistData> artist;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Tags {
    public List<Tag> tag;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Tag {
    public String name;
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Bio {
    public String published;
    public String summary;
    public String content;
  }
}

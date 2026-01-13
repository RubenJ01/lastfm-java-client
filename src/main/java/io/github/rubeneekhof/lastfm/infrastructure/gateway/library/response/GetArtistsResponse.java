package io.github.rubeneekhof.lastfm.infrastructure.gateway.library.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetArtistsResponse {

  public Artists artists;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Artists {
    @JsonProperty("@attr")
    public Attr attr;

    public List<ArtistData> artist;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Attr {
    public String user;
    public Object page;
    public Object perPage;
    public Object totalPages;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ArtistData {
    public String name;
    public Object playcount;
    public Object tagcount;
    public String mbid;
    public String url;
    public String streamable;
    public List<Image> image;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Image {
    public String size;

    @JsonProperty("#text")
    public String url;
  }
}

package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

  public Results results;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Results {
    @JsonProperty("opensearch:totalResults")
    public String totalResults;

    @JsonProperty("opensearch:startIndex")
    public String startIndex;

    @JsonProperty("opensearch:itemsPerPage")
    public String itemsPerPage;

    public ArtistMatches artistmatches;

    @JsonProperty("@attr")
    public Attr attr;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ArtistMatches {
    public List<ArtistData> artist;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ArtistData {
    public String name;
    public String listeners;
    public String mbid;
    public String url;
    public String streamable;
    public List<Image> image;
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
  public static class Attr {
    @JsonProperty("for")
    public String for_;
  }
}

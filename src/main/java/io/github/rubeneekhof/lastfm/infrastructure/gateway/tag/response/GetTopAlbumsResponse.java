package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTopAlbumsResponse {

  public Albums albums;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Albums {
    @JsonProperty("@attr")
    public Attr attr;

    public List<AlbumData> album;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Attr {
    public String tag;
    public String page;
    public String perPage;
    public String totalPages;
    public String total;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumData {
    public String name;
    public String mbid;
    public String url;
    public Artist artist;
    public List<Image> image;

    @JsonProperty("@attr")
    public AlbumDataAttr attr;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Artist {
    public String name;
    public String mbid;
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Image {
    public String size;

    @JsonProperty("#text")
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumDataAttr {
    public String rank;
  }
}

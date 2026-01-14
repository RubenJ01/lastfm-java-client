package io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetInfoResponse {

  public TrackData track;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TrackData {
    public String name;
    public String mbid;
    public String url;
    public String duration;
    public Streamable streamable;
    public String listeners;
    public String playcount;
    public TrackArtist artist;
    public TrackAlbum album;
    public String userplaycount;
    public String userloved;
    public TopTags toptags;
    public Wiki wiki;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Streamable {
    @JsonProperty("#text")
    public String text;

    public String fulltrack;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TrackArtist {
    public String name;
    public String mbid;
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TrackAlbum {
    public String artist;
    public String title;
    public String mbid;
    public String url;
    public List<Image> image;

    @JsonProperty("@attr")
    public AlbumAttr attr;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumAttr {
    public String position;
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
  public static class TopTags {
    public List<Tag> tag;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Tag {
    public String name;
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Wiki {
    public String published;
    public String summary;
    public String content;
  }
}

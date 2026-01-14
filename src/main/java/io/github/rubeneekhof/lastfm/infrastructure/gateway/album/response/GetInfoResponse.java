package io.github.rubeneekhof.lastfm.infrastructure.gateway.album.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseWikiResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetInfoResponse {

  public AlbumData album;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumData {
    public String name;
    public String artist;
    public String id;
    public String mbid;
    public String url;
    public String releasedate;
    public List<Image> image;
    public String listeners;
    public String playcount;
    public Object userplaycount;
    public Object tags;
    public Tracks tracks;
    public Wiki wiki;
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
  public static class Tags {
    public List<Tag> tag;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Tag {
    public String name;
    public String url;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Wiki implements BaseWikiResponse {
    public String published;
    public String summary;
    public String content;

      @Override
      public String getPublished() {
          return published;
      }

      @Override
      public String getSummary() {
          return summary;
      }

      @Override
      public String getContent() {
          return content;
      }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Tracks {
    public List<Track> track;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Track {
    @JsonProperty("@attr")
    public TrackAttr attr;

    public String name;
    public Object duration;
    public String mbid;
    public String url;
    public Streamable streamable;
    public TrackArtist artist;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Streamable {
    public String fulltrack;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TrackAttr {
    public String rank;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TrackArtist {
    public String name;
    public String mbid;
    public String url;
  }
}

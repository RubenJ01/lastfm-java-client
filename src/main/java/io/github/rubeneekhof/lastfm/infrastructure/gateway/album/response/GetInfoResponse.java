package io.github.rubeneekhof.lastfm.infrastructure.gateway.album.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  public static class Image {
    public String size;

    @JsonProperty("#text")
    public String url;
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
  public static class Wiki {
    public String published;
    public String summary;
    public String content;
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

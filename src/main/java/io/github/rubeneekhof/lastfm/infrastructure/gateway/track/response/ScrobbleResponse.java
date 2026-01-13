package io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScrobbleResponse {

  public Scrobbles scrobbles;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Scrobbles {
    public String accepted;
    public String ignored;
    public Object scrobble; // Can be a single Scrobble object or a List<Scrobble>
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Scrobble {
    public Track track;
    public Artist artist;
    public Album album;
    public AlbumArtist albumArtist;
    public String timestamp;
    public IgnoredMessage ignoredMessage;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Track {
    @JsonProperty("@corrected")
    public String corrected;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Artist {
    @JsonProperty("@corrected")
    public String corrected;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Album {
    @JsonProperty("@corrected")
    public String corrected;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumArtist {
    @JsonProperty("@corrected")
    public String corrected;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class IgnoredMessage {
    @JsonProperty("@code")
    public String code;
  }
}

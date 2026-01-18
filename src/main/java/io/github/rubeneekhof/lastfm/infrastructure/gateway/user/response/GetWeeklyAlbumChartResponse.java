package io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWeeklyAlbumChartResponse {

  public WeeklyAlbumChart weeklyalbumchart;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class WeeklyAlbumChart {
    public List<AlbumData> album;

    @JsonProperty("@attr")
    public Attr attr;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumData {
    public Artist artist;
    public String mbid;
    public String playcount;
    public String name;
    public String url;

    @JsonProperty("@attr")
    public AlbumAttr attr;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Artist {
    public String mbid;

    @JsonProperty("#text")
    public String text;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AlbumAttr {
    public String rank;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Attr {
    public String user;
    public String from;
    public String to;
  }
}

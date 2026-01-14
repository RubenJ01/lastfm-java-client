package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseWikiResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetInfoResponse {

  public TagData tag;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TagData {
    public String name;
    public String url;
    public Object reach;
    public Object total;
    public Wiki wiki;
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
}

package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTopTagsResponse {

  public TopTags toptags;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TopTags {
    @JsonProperty("@attr")
    public Attr attr;

    public List<TagData> tag;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Attr {
    public String offset;
    public String num_res;
    public String total;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TagData {
    public String name;
    public String count;
    public String reach;
  }
}

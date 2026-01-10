package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public static class Wiki {
        public String published;
        public String summary;
        public String content;
    }
}

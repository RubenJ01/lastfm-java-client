package io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTopArtistsResponse {

    public Artists artists;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artists {
        @JsonProperty("@attr")
        public Attr attr;
        public List<ArtistData> artist;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Attr {
        public String page;
        public String perPage;
        public String totalPages;
        public String total;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArtistData {
        public String name;
        public String playcount;
        public String listeners;
        public String mbid;
        public String url;
        public String streamable;
        public List<Image> image;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        public String size;

        @JsonProperty("#text")
        public String url;
    }
}

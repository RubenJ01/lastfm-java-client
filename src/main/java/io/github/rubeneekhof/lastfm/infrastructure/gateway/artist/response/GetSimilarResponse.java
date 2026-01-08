package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSimilarResponse {

    public SimilarArtists similarartists;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SimilarArtists {
        public List<SimilarArtistData> artist;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SimilarArtistData {
        public String name;
        public String mbid;
        public String url;
        public double match; // similarity score 0..1
        public List<Image> image;
        public String streamable;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        public String size;

        @JsonProperty("#text")
        public String url;
    }
}

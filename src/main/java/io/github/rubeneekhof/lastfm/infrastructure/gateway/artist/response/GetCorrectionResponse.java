package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCorrectionResponse {

    public Corrections corrections;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Corrections {
        public Correction correction;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Correction {
        public CorrectionArtist artist;
        
        @JsonProperty("@attr")
        public Attr attr;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Attr {
        public String index;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CorrectionArtist {
        public String name;
        public String mbid;
        public String url;
    }
}

package io.github.rubeneekhof.lastfm.application.artist;

public class ArtistGetSimilarRequest {
    private final String artist;
    private final String mbid;
    private final Boolean autocorrect;
    private final Integer limit;

    private ArtistGetSimilarRequest(Builder builder) {
        this.artist = builder.artist;
        this.mbid = builder.mbid;
        this.autocorrect = builder.autocorrect;
        this.limit = builder.limit;
    }

    public static Builder artist(String artist) {
        return new Builder().artist(artist);
    }

    public static Builder mbid(String mbid) {
        return new Builder().mbid(mbid);
    }

    public String artist() {
        return artist;
    }

    public String mbid() {
        return mbid;
    }

    public Boolean autocorrect() {
        return autocorrect;
    }

    public Integer limit() {
        return limit;
    }

    public static class Builder {
        private String artist;
        private String mbid;
        private Boolean autocorrect;
        private Integer limit;

        public Builder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder mbid(String mbid) {
            this.mbid = mbid;
            return this;
        }

        public Builder autocorrect(boolean autocorrect) {
            this.autocorrect = autocorrect;
            return this;
        }

        public Builder limit(int limit) {
            if (limit <= 0) {
                throw new IllegalArgumentException("Limit must be greater than zero");
            }
            this.limit = limit;
            return this;
        }

        public ArtistGetSimilarRequest build() {
            if (artist == null && mbid == null) {
                throw new IllegalArgumentException("Either artist name or mbid must be provided");
            }
            return new ArtistGetSimilarRequest(this);
        }
    }
}

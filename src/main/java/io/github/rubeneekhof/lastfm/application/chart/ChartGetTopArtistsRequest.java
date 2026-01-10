package io.github.rubeneekhof.lastfm.application.chart;

public record ChartGetTopArtistsRequest(Integer page, Integer limit) {

    public static Builder page(int page) {
        return new Builder().page(page);
    }

    public static Builder limit(int limit) {
        return new Builder().limit(limit);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ChartGetTopArtistsRequest defaults() {
        return new ChartGetTopArtistsRequest(null, null);
    }

    public static class Builder {
        private Integer page;
        private Integer limit;

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public ChartGetTopArtistsRequest build() {
            return new ChartGetTopArtistsRequest(page, limit);
        }
    }
}

package io.github.rubeneekhof.lastfm.application.library;

public record LibraryGetArtistsRequest(
        String user,
        Integer page,
        Integer limit
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String user;
        private Integer page;
        private Integer limit;

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public LibraryGetArtistsRequest build() {
            if (user == null || user.isBlank()) {
                throw new IllegalArgumentException("User must not be blank");
            }
            return new LibraryGetArtistsRequest(user, page, limit);
        }
    }
}

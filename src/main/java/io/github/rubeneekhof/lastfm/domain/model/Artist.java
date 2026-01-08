package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record Artist(
        String name,
        String mbid,
        String url,
        List<Image> images,
        Stats stats,
        List<Artist> similar,
        List<Tag> tags,
        Bio bio
) {
    public record Image(String size, String url) {}
    public record Stats(int listeners, int plays) {}
    public record Tag(String name, String url) {}
    public record Bio(String published, String summary, String content) {}
}

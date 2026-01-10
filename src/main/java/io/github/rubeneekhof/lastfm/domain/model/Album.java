package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record Album(
        String name,
        String artist,
        String id,
        String mbid,
        String url,
        String releaseDate,
        List<Image> images,
        Stats stats,
        List<Tag> tags,
        List<Track> tracks
) {
    public record Image(String size, String url) {
        @Override
        public String toString() {
            return "Image { size: '" + size + "', url: '" + url + "' }";
        }
    }

    public record Stats(int listeners, int plays, Integer userplaycount) {
        @Override
        public String toString() {
            String result = "Stats { listeners: " + listeners + ", plays: " + plays;
            if (userplaycount != null) {
                result += ", userplaycount: " + userplaycount;
            }
            return result + " }";
        }
    }

    public record Tag(String name, String url) {
        @Override
        public String toString() {
            return "Tag { name: '" + name + "', url: '" + url + "' }";
        }
    }

    public record Track(
            String name,
            Integer duration,
            String mbid,
            String url,
            String streamable,
            String artistName,
            String artistMbid,
            String artistUrl,
            Integer rank
    ) {
        @Override
        public String toString() {
            return "Track { name: '" + name + "', duration: " + duration + ", rank: " + rank + " }";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Album {\n");
        sb.append("    name: '").append(name).append("',\n");
        sb.append("    artist: '").append(artist).append("',\n");
        sb.append("    id: '").append(id).append("',\n");
        sb.append("    mbid: '").append(mbid).append("',\n");
        sb.append("    url: '").append(url).append("',\n");
        sb.append("    releaseDate: '").append(releaseDate != null ? releaseDate : "null").append("',\n");

        sb.append("    images: [\n");
        if (images != null) {
            for (Image img : images) {
                sb.append("        ").append(img).append(",\n");
            }
        }
        sb.append("    ],\n");

        sb.append("    stats: ").append(stats != null ? stats : "null").append(",\n");

        sb.append("    tags: [\n");
        if (tags != null) {
            for (Tag tag : tags) {
                sb.append("        ").append(tag).append(",\n");
            }
        }
        sb.append("    ],\n");

        sb.append("    tracks: [\n");
        if (tracks != null) {
            for (Track track : tracks) {
                sb.append("        ").append(track).append(",\n");
            }
        }
        sb.append("    ]\n");

        sb.append("}");
        return sb.toString();
    }
}

package io.github.rubeneekhof.lastfm.domain.model.user;

import io.github.rubeneekhof.lastfm.domain.model.Track;
import java.util.List;

public record LovedTracksResult(
        String user,
        int page,
        int total,
        int perPage,
        int totalPages,
        List<LovedTrack> tracks) {

    public record LovedTrack(
            String name,
            String mbid,
            String url,
            Track.Artist artist,
            List<Image> images,
            Track.Streamable streamable,
            long date) {

        public record Image(String size, String url) {
            @Override
            public String toString() {
                return "Image { size: '" + size + "', url: '" + url + "' }";
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("LovedTrack {\n");
            sb.append("    name: '").append(name).append("',\n");
            sb.append("    mbid: '").append(mbid != null ? mbid : "").append("',\n");
            sb.append("    url: '").append(url).append("',\n");
            sb.append("    artist: ").append(artist != null ? artist : "null").append(",\n");
            sb.append("    date: ").append(date).append(",\n");
            sb.append("    streamable: ").append(streamable != null ? streamable : "null").append(",\n");
            sb.append("    images: [\n");

            if (images != null) {
                for (Image img : images) {
                    sb.append("        ").append(img).append(",\n");
                }
            }

            sb.append("    ]\n");
            sb.append("}");
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LovedTracksResult {\n");
        sb.append("    user: '").append(user).append("',\n");
        sb.append("    page: ").append(page).append(",\n");
        sb.append("    total: ").append(total).append(",\n");
        sb.append("    perPage: ").append(perPage).append(",\n");
        sb.append("    totalPages: ").append(totalPages).append(",\n");
        sb.append("    tracks: [\n");

        if (tracks != null) {
            for (LovedTrack track : tracks) {
                sb.append(track.toString().indent(8).stripTrailing())
                        .append(",\n");
            }
        }

        sb.append("    ]\n");
        sb.append("}");
        return sb.toString();
    }
}

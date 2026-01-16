package io.github.rubeneekhof.lastfm.domain.model.artist;

import java.util.List;

public record ArtistSearchResult(
        int totalResults,
        int startIndex,
        int itemsPerPage,
        List<Artist> artists
) {

    private static String indent(String text, int spaces) {
        String prefix = " ".repeat(spaces);
        return text.replace("\n", "\n" + prefix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArtistSearchResult {\n");
        sb.append("    totalResults: ").append(totalResults).append(",\n");
        sb.append("    startIndex: ").append(startIndex).append(",\n");
        sb.append("    itemsPerPage: ").append(itemsPerPage).append(",\n");
        sb.append("    artists: [\n");
        if (artists != null) {
            for (Artist artist : artists) {
                sb.append("        ")
                        .append(indent(artist.toString(), 8))
                        .append(",\n");
            }
        }
        sb.append("    ]\n");
        sb.append("}");
        return sb.toString();
    }
}
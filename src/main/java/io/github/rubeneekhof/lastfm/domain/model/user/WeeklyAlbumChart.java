package io.github.rubeneekhof.lastfm.domain.model.user;

import java.util.List;

public record WeeklyAlbumChart(String user, Long from, Long to, List<Album> albums) {
  public record Album(
      String name, String artist, String mbid, String url, int playcount, int rank) {
    @Override
    public String toString() {
      return "Album { name: '"
          + name
          + "', artist: '"
          + artist
          + "', playcount: "
          + playcount
          + ", rank: "
          + rank
          + " }";
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("WeeklyAlbumChart {\n");
    sb.append("    user: '").append(user).append("',\n");
    if (from != null) {
      sb.append("    from: ").append(from).append(",\n");
    }
    if (to != null) {
      sb.append("    to: ").append(to).append(",\n");
    }
    sb.append("    albums: [\n");
    if (albums != null) {
      for (Album album : albums) {
        sb.append("        ").append(album).append(",\n");
      }
    }
    sb.append("    ]\n");
    sb.append("}");
    return sb.toString();
  }
}

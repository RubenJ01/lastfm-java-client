package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record TagAlbum(
    String name, String mbid, String url, Artist artist, List<Image> images, int rank) {
  public record Artist(String name, String mbid, String url) {
    @Override
    public String toString() {
      return "Artist { name: '"
          + name
          + "', mbid: '"
          + (mbid != null ? mbid : "null")
          + "', url: '"
          + url
          + "' }";
    }
  }

  public record Image(String size, String url) {
    @Override
    public String toString() {
      return "Image { size: '" + size + "', url: '" + url + "' }";
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("TagAlbum {\n");
    sb.append("    name: '").append(name).append("',\n");
    sb.append("    mbid: '").append(mbid != null ? mbid : "null").append("',\n");
    sb.append("    url: '").append(url).append("',\n");
    sb.append("    artist: ").append(artist != null ? artist : "null").append(",\n");
    sb.append("    rank: ").append(rank).append(",\n");
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

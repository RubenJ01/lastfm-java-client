package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record LibraryArtist(
    String name,
    String mbid,
    String url,
    List<Image> images,
    String streamable,
    int playcount,
    int tagcount,
    int rank) {
  public record Image(String size, String url) {
    @Override
    public String toString() {
      return "Image { size: '" + size + "', url: '" + url + "' }";
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("LibraryArtist {\n");
    sb.append("    name: '").append(name).append("',\n");
    sb.append("    mbid: '").append(mbid != null ? mbid : "null").append("',\n");
    sb.append("    url: '").append(url).append("',\n");
    sb.append("    streamable: '").append(streamable != null ? streamable : "null").append("',\n");
    sb.append("    playcount: ").append(playcount).append(",\n");
    sb.append("    tagcount: ").append(tagcount).append(",\n");
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

package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record User(
    String name,
    String realname,
    String url,
    String country,
    String gender,
    int age,
    int playlists,
    long playcount,
    boolean subscriber,
    String type,
    long registered,
    int bootstrap,
    List<Image> images) {

  public record Image(String size, String url) {
    @Override
    public String toString() {
        return "Image { size: '" + size + "', url: '" + url + "' }";
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
            sb.append("User {\n");
                sb.append("    name: '").append(name).append("',\n");
    if (realname != null) {
      sb.append("    realname: '").append(realname).append("',\n");
    }
    sb.append("    url: '").append(url).append("',\n");
    if (country != null) {
      sb.append("    country: '").append(country).append("',\n");
    }
    if (gender != null) {
      sb.append("    gender: '").append(gender).append("',\n");
    }
    sb.append("    age: ").append(age).append(",\n");
    sb.append("    playlists: ").append(playlists).append(",\n");
    sb.append("    playcount: ").append(playcount).append(",\n");
    sb.append("    subscriber: ").append(subscriber).append(",\n");
    sb.append("    type: '").append(type).append("',\n");
    sb.append("    registered: ").append(registered).append(",\n");
    sb.append("    bootstrap: ").append(bootstrap).append(",\n");

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

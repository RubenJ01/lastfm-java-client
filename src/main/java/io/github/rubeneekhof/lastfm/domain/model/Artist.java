package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record Artist(
    String name,
    String mbid,
    String url,
    List<Image> images,
    String streamable,
    Stats stats,
    List<Artist> similar,
    List<Tag> tags,
    Bio bio,
    Double match) {
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

  public record Bio(String published, String summary, String content) {
    @Override
    public String toString() {
      return "Bio { published: '"
          + published
          + "', summary: '"
          + summary
          + "', content: '"
          + content
          + "' }";
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Artist {\n");
    sb.append("    name: '").append(name).append("',\n");
    sb.append("    mbid: '").append(mbid).append("',\n");
    sb.append("    url: '").append(url).append("',\n");
    sb.append("    streamable: '").append(streamable != null ? streamable : "null").append("',\n");

    sb.append("    images: [\n");
    if (images != null) {
      for (Image img : images) {
        sb.append("        ").append(img).append(",\n");
      }
    }
    sb.append("    ],\n");

    sb.append("    stats: ").append(stats != null ? stats : "null").append(",\n");
    if (match != null) {
      sb.append("    match: ").append(match).append(",\n");
    }

    sb.append("    similar: [\n");
    if (similar != null) {
      for (Artist art : similar) {
        sb.append("        '").append(art.name).append("',\n");
      }
    }
    sb.append("    ],\n");

    sb.append("    tags: [\n");
    if (tags != null) {
      for (Tag tag : tags) {
        sb.append("        ").append(tag).append(",\n");
      }
    }
    sb.append("    ],\n");

    sb.append("    bio: ");
    if (bio != null) {
      sb.append("Bio {\n");
      sb.append("        published: '").append(bio.published).append("',\n");
      sb.append("        summary: '")
          .append(bio.summary.replace("\n", "\n        "))
          .append("',\n");
      sb.append("        content: '").append(bio.content.replace("\n", "\n        ")).append("'\n");
      sb.append("    }");
    } else {
      sb.append("null");
    }
    sb.append("\n}");

    return sb.toString();
  }
}

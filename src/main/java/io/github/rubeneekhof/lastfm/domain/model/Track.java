package io.github.rubeneekhof.lastfm.domain.model;

import java.util.List;

public record Track(
    String name,
    String mbid,
    String url,
    Integer duration,
    Streamable streamable,
    Stats stats,
    Artist artist,
    Album album,
    Integer userplaycount,
    Boolean userloved,
    List<Tag> tags,
    Wiki wiki) {

  public record Streamable(boolean text, boolean fulltrack) {
    @Override
    public String toString() {
      return "Streamable { text: " + text + ", fulltrack: " + fulltrack + " }";
    }
  }

  public record Stats(long listeners, long plays) {
    @Override
    public String toString() {
      return "Stats { listeners: " + listeners + ", plays: " + plays + " }";
    }
  }

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

  public record Album(
      String artist, String title, String mbid, String url, List<Image> images, Integer position) {
    public record Image(String size, String url) {
      @Override
      public String toString() {
        return "Image { size: '" + size + "', url: '" + url + "' }";
      }
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Album {\n");
      sb.append("        artist: '").append(artist).append("',\n");
      sb.append("        title: '").append(title).append("',\n");
      sb.append("        mbid: '").append(mbid != null ? mbid : "null").append("',\n");
      sb.append("        url: '").append(url).append("',\n");
      sb.append("        position: ").append(position != null ? position : "null").append(",\n");
      sb.append("        images: [\n");
      if (images != null) {
        for (Image img : images) {
          sb.append("            ").append(img).append(",\n");
        }
      }
      sb.append("        ]\n");
      sb.append("    }");
      return sb.toString();
    }
  }

  public record Tag(String name, String url) {
    @Override
    public String toString() {
      return "Tag { name: '" + name + "', url: '" + url + "' }";
    }
  }

  public record Wiki(String published, String summary, String content) {
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Wiki {\n");
      sb.append("        published: '")
          .append(published != null ? published : "null")
          .append("',\n");
      sb.append("        summary: '").append(summary != null ? summary : "null").append("',\n");
      sb.append("        content: '").append(content != null ? content : "null").append("'\n");
      sb.append("    }");
      return sb.toString();
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Track {\n");
    sb.append("    name: '").append(name).append("',\n");
    sb.append("    mbid: '").append(mbid != null ? mbid : "null").append("',\n");
    sb.append("    url: '").append(url).append("',\n");
    sb.append("    duration: ").append(duration != null ? duration : "null").append(",\n");
    sb.append("    streamable: ").append(streamable != null ? streamable : "null").append(",\n");
    sb.append("    stats: ").append(stats != null ? stats : "null").append(",\n");
    sb.append("    artist: ").append(artist != null ? artist : "null").append(",\n");
    sb.append("    album: ").append(album != null ? album : "null").append(",\n");
    sb.append("    userplaycount: ")
        .append(userplaycount != null ? userplaycount : "null")
        .append(",\n");
    sb.append("    userloved: ").append(userloved != null ? userloved : "null").append(",\n");
    sb.append("    tags: [\n");
    if (tags != null) {
      for (Tag tag : tags) {
        sb.append("        ").append(tag).append(",\n");
      }
    }
    sb.append("    ],\n");
    sb.append("    wiki: ").append(wiki != null ? wiki : "null").append("\n");
    sb.append("}");
    return sb.toString();
  }
}

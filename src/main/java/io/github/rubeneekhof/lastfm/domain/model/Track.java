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

  public record Streamable(boolean text, boolean fulltrack) {}

  public record Stats(long listeners, long plays) {
    @Override
    public String toString() {
      return "Stats { listeners: " + listeners + ", plays: " + plays + " }";
    }
  }

  public record Artist(String name, String mbid, String url) {
    @Override
    public String toString() {
      return "Artist { name: '" + name + "', mbid: '" + mbid + "', url: '" + url + "' }";
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
      return "Album { title: '"
          + title
          + "', artist: '"
          + artist
          + "', position: "
          + position
          + " }";
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
      return "Wiki { published: '"
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
    sb.append("Track {\n");
    sb.append("    name: '").append(name).append("',\n");
    if (mbid != null) {
      sb.append("    mbid: '").append(mbid).append("',\n");
    }
    sb.append("    url: '").append(url).append("',\n");
    if (duration != null) {
      sb.append("    duration: ").append(duration).append(",\n");
    }
    if (streamable != null) {
      sb.append("    streamable: ")
          .append(streamable.text)
          .append(" (fulltrack: ")
          .append(streamable.fulltrack)
          .append("),\n");
    }
    if (stats != null) {
      sb.append("    stats: ").append(stats).append(",\n");
    }
    if (artist != null) {
      sb.append("    artist: ").append(artist).append(",\n");
    }
    if (album != null) {
      sb.append("    album: ").append(album).append(",\n");
    }
    if (userplaycount != null) {
      sb.append("    userplaycount: ").append(userplaycount).append(",\n");
    }
    if (userloved != null) {
      sb.append("    userloved: ").append(userloved).append(",\n");
    }
    if (tags != null && !tags.isEmpty()) {
      sb.append("    tags: [\n");
      for (Tag tag : tags) {
        sb.append("        ").append(tag).append(",\n");
      }
      sb.append("    ],\n");
    }
    if (wiki != null) {
      sb.append("    wiki: ").append(wiki).append("\n");
    }
    sb.append("}");
    return sb.toString();
  }
}

package io.github.rubeneekhof.lastfm.domain.model.tag;

public record Tag(String name, String url, int reach, int taggings, Wiki wiki) {
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
    sb.append("Tag {\n");
    sb.append("    name: '").append(name).append("',\n");
    sb.append("    url: '").append(url).append("',\n");
    sb.append("    reach: ").append(reach).append(",\n");
    sb.append("    taggings: ").append(taggings).append(",\n");
    sb.append("    wiki: ");
    if (wiki != null) {
      sb.append(wiki);
    } else {
      sb.append("null");
    }
    sb.append("\n}");
    return sb.toString();
  }
}

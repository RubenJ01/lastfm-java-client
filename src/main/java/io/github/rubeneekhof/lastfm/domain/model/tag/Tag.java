package io.github.rubeneekhof.lastfm.domain.model.tag;

public record Tag(String name, String url, int reach, int taggings, Wiki wiki) {
    public record Wiki(String published, String summary, String content) {
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Wiki {\n");
            sb.append("        published: '").append(published != null ? published : "null").append("',\n");
            sb.append("        summary: '").append(summary != null ? summary : "null").append("',\n");
            sb.append("        content: '").append(content != null ? content : "null").append("'\n");
            sb.append("    }");
            return sb.toString();
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
        sb.append("    wiki: ").append(wiki != null ? wiki : "null").append("\n");
        sb.append("}");
        return sb.toString();
    }
}

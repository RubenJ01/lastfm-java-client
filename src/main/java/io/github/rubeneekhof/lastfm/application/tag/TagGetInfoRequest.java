package io.github.rubeneekhof.lastfm.application.tag;

public record TagGetInfoRequest(String tag, String lang) {

  public static Builder tag(String tag) {
    return new Builder().tag(tag);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String tag;
    private String lang;

    public Builder tag(String tag) {
      this.tag = tag;
      return this;
    }

    public Builder lang(String lang) {
      this.lang = lang;
      return this;
    }

    public TagGetInfoRequest build() {
      if (tag == null || tag.isBlank()) {
        throw new IllegalArgumentException("Tag name must not be blank");
      }
      return new TagGetInfoRequest(tag, lang);
    }
  }
}

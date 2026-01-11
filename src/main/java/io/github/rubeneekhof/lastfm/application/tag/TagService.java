package io.github.rubeneekhof.lastfm.application.tag;

import io.github.rubeneekhof.lastfm.domain.model.Tag;
import io.github.rubeneekhof.lastfm.domain.port.TagGateway;

public class TagService {

  private final TagGateway gateway;

  public TagService(TagGateway gateway) {
    this.gateway = gateway;
  }

  public Tag getInfo(String tag) {
    validateTagName(tag);
    return getInfo(TagGetInfoRequest.tag(tag).build());
  }

  public Tag getInfo(String tag, String lang) {
    validateTagName(tag);
    return getInfo(TagGetInfoRequest.tag(tag).lang(lang).build());
  }

  public Tag getInfo(TagGetInfoRequest request) {
    return gateway.getInfo(request.tag(), request.lang());
  }

  private void validateTagName(String tagName) {
    if (tagName == null || tagName.isBlank()) {
      throw new IllegalArgumentException("Tag name must not be blank");
    }
  }
}

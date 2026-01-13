package io.github.rubeneekhof.lastfm.application.tag;

import io.github.rubeneekhof.lastfm.domain.model.tag.Tag;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagAlbum;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagArtist;
import io.github.rubeneekhof.lastfm.domain.model.tag.TopTag;
import io.github.rubeneekhof.lastfm.domain.port.TagGateway;
import java.util.List;

public class TagService {

  private static final int DEFAULT_LIMIT = 50;
  private static final int DEFAULT_PAGE = 1;
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
    System.out.println(1111111);
    System.out.println("test");
    return getInfo(TagGetInfoRequest.tag(tag).lang(lang).build());
  }

  public Tag getInfo(TagGetInfoRequest request) {
    return gateway.getInfo(request.tag(), request.lang());
  }

  public List<TagAlbum> getTopAlbums(String tag) {
    validateTagName(tag);
    return getTopAlbums(
        TagGetTopAlbumsRequest.builder().tag(tag).page(DEFAULT_PAGE).limit(DEFAULT_LIMIT).build());
  }

  public List<TagAlbum> getTopAlbums(String tag, int page) {
    validateTagName(tag);
    validatePage(page);
    return getTopAlbums(
        TagGetTopAlbumsRequest.builder().tag(tag).page(page).limit(DEFAULT_LIMIT).build());
  }

  public List<TagAlbum> getTopAlbums(String tag, int page, int limit) {
    validateTagName(tag);
    validatePage(page);
    validateLimit(limit);
    return getTopAlbums(TagGetTopAlbumsRequest.builder().tag(tag).page(page).limit(limit).build());
  }

  public List<TagAlbum> getTopAlbums(TagGetTopAlbumsRequest request) {
    return gateway.getTopAlbums(request.tag(), request.limit(), request.page());
  }

  public List<TagArtist> getTopArtists(String tag) {
    validateTagName(tag);
    return getTopArtists(
        TagGetTopArtistsRequest.tag(tag).page(DEFAULT_PAGE).limit(DEFAULT_LIMIT).build());
  }

  public List<TagArtist> getTopArtists(String tag, int page) {
    validateTagName(tag);
    validatePage(page);
    return getTopArtists(TagGetTopArtistsRequest.tag(tag).page(page).limit(DEFAULT_LIMIT).build());
  }

  public List<TagArtist> getTopArtists(String tag, int page, int limit) {
    validateTagName(tag);
    validatePage(page);
    validateLimit(limit);
    return getTopArtists(TagGetTopArtistsRequest.tag(tag).page(page).limit(limit).build());
  }

  public List<TagArtist> getTopArtists(TagGetTopArtistsRequest request) {
    return gateway.getTopArtists(request.tag(), request.limit(), request.page());
  }

  public List<TopTag> getTopTags() {
    return gateway.getTopTags();
  }

  private void validateTagName(String tagName) {
    if (tagName == null || tagName.isBlank()) {
      throw new IllegalArgumentException("Tag name must not be blank");
    }
  }

  private void validatePage(int page) {
    if (page <= 0) {
      throw new IllegalArgumentException("Page must be greater than zero");
    }
  }

  private void validateLimit(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("Limit must be greater than zero");
    }
  }
}

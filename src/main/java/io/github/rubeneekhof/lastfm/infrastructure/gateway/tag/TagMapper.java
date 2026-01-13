package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag;

import io.github.rubeneekhof.lastfm.domain.model.Tag;
import io.github.rubeneekhof.lastfm.domain.model.TagAlbum;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopAlbumsResponse;
import java.util.List;
import java.util.Optional;

public class TagMapper {

  public static Tag from(GetInfoResponse response) {
    if (response == null || response.tag == null) {
      return null;
    }

    GetInfoResponse.TagData data = response.tag;
    return new Tag(
        data.name, data.url, parseNumber(data.reach), parseNumber(data.total), mapWiki(data.wiki));
  }

  public static List<TagAlbum> from(GetTopAlbumsResponse response) {
    if (response == null || response.albums == null || response.albums.album == null) {
      return List.of();
    }

    return response.albums.album.stream().map(TagMapper::from).toList();
  }

  private static TagAlbum from(GetTopAlbumsResponse.AlbumData data) {
    if (data == null) {
      return null;
    }

    return new TagAlbum(
        data.name,
        data.mbid,
        data.url,
        new TagAlbum.Artist(data.artist.name, data.artist.mbid, data.artist.url),
        mapImages(data.image),
        Integer.parseInt(data.attr.rank));
  }

  private static List<TagAlbum.Image> mapImages(List<GetTopAlbumsResponse.Image> images) {
    return Optional.ofNullable(images)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(img -> new TagAlbum.Image(img.size, img.url)).toList())
        .orElse(List.of());
  }

  private static int parseNumber(Object value) {
    if (value == null) {
      return 0;
    }

    if (value instanceof Number) {
      return ((Number) value).intValue();
    }

    if (value instanceof String) {
      String str = (String) value;
      if (str.isBlank()) {
        return 0;
      }
      try {
        return Integer.parseInt(str);
      } catch (NumberFormatException e) {
        try {
          return (int) Double.parseDouble(str);
        } catch (NumberFormatException ex) {
          return 0;
        }
      }
    }

    return 0;
  }

  private static Tag.Wiki mapWiki(GetInfoResponse.Wiki wiki) {
    if (wiki == null) {
      return null;
    }
    return new Tag.Wiki(wiki.published, wiki.summary, wiki.content);
  }
}

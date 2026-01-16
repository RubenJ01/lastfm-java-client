package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag;

import io.github.rubeneekhof.lastfm.domain.model.tag.Tag;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagAlbum;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagArtist;
import io.github.rubeneekhof.lastfm.domain.model.tag.TopTag;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopAlbumsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopTagsResponse;
import java.util.List;

public class TagMapper extends BaseMapper {

  public static Tag from(GetInfoResponse response) {
    if (response == null || response.tag == null) {
      return null;
    }

    GetInfoResponse.TagData data = response.tag;
    return new Tag(
        data.name,
        data.url,
        parseNumber(data.reach),
        parseNumber(data.total),
        mapWiki(
            data.wiki,
            wiki -> new Tag.Wiki(wiki.getPublished(), wiki.getSummary(), wiki.getContent())));
  }

  public static List<TagAlbum> from(GetTopAlbumsResponse response) {
    if (response == null || response.albums == null || response.albums.album == null) {
      return List.of();
    }

    return response.albums.album.stream().map(TagMapper::from).toList();
  }

  public static List<TagArtist> from(GetTopArtistsResponse response) {
    if (response == null || response.topartists == null || response.topartists.artist == null) {
      return List.of();
    }

    return response.topartists.artist.stream().map(TagMapper::from).toList();
  }

  private static TagArtist from(GetTopArtistsResponse.ArtistData data) {
    if (data == null) {
      return null;
    }

    return new TagArtist(
        data.name,
        data.mbid,
        data.url,
        data.streamable,
        mapImages(data.image, img -> new TagArtist.Image(img.getSize(), img.getUrl())),
        Integer.parseInt(data.attr.rank));
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
        mapImages(data.image, img -> new TagAlbum.Image(img.getSize(), img.getUrl())),
        Integer.parseInt(data.attr.rank));
  }

  public static List<TopTag> from(GetTopTagsResponse response) {
    if (response == null || response.toptags == null || response.toptags.tag == null) {
      return List.of();
    }

    return response.toptags.tag.stream().map(TagMapper::from).toList();
  }

  private static TopTag from(GetTopTagsResponse.TagData data) {
    if (data == null) {
      return null;
    }

    return new TopTag(data.name, parseLong(data.count), parseLong(data.reach));
  }

}

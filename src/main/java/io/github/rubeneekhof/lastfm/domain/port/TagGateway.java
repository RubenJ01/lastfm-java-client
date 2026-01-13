package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.tag.Tag;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagAlbum;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagArtist;
import io.github.rubeneekhof.lastfm.domain.model.tag.TopTag;
import java.util.List;

public interface TagGateway extends LastFmApiGateway {
  Tag getInfo(String tag, String lang);

  List<TagAlbum> getTopAlbums(String tag, Integer limit, Integer page);

  List<TagArtist> getTopArtists(String tag, Integer limit, Integer page);

  List<TopTag> getTopTags();
}

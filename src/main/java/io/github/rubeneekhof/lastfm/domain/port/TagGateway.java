package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Tag;
import io.github.rubeneekhof.lastfm.domain.model.TagAlbum;
import java.util.List;

public interface TagGateway extends LastFmApiGateway {
  Tag getInfo(String tag, String lang);
  List<TagAlbum> getTopAlbums(String tag, Integer limit, Integer page);
}

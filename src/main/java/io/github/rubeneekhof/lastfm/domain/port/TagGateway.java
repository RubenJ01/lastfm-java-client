package io.github.rubeneekhof.lastfm.domain.port;

import io.github.rubeneekhof.lastfm.domain.model.Tag;

public interface TagGateway extends LastFmApiGateway {
  Tag getInfo(String tag, String lang);
}

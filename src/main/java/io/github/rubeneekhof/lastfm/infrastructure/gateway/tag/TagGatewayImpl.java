package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Tag;
import io.github.rubeneekhof.lastfm.domain.model.TagAlbum;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.TagGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.LastFmErrorMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopAlbumsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagGatewayImpl implements TagGateway {

  private final HttpExecutor http;
  private final ObjectMapper mapper;

  public TagGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    this.http = http;
    this.mapper = mapper;
  }

  @Override
  public Tag getInfo(String tag, String lang) {
    try {
      Map<String, String> params = new HashMap<>();
      params.put("tag", tag);

      if (lang != null) {
        params.put("lang", lang);
      }

      GetInfoResponse response =
          getAndParse(http, mapper, "tag.getinfo", params, GetInfoResponse.class);
      return TagMapper.from(response);
    } catch (LastFmException e) {
      throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
    }
  }

  @Override
  public List<TagAlbum> getTopAlbums(String tag, Integer limit, Integer page) {
    try {
      Map<String, String> params = new HashMap<>();
      params.put("tag", tag);

      if (page != null) {
        params.put("page", String.valueOf(page));
      }
      if (limit != null) {
        params.put("limit", String.valueOf(limit));
      }

      GetTopAlbumsResponse response =
          getAndParse(http, mapper, "tag.gettopalbums", params, GetTopAlbumsResponse.class);

      return TagMapper.from(response);
    } catch (LastFmException e) {
      throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
    }
  }
}

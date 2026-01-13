package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.tag.Tag;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagAlbum;
import io.github.rubeneekhof.lastfm.domain.model.tag.TagArtist;
import io.github.rubeneekhof.lastfm.domain.model.tag.TopTag;
import io.github.rubeneekhof.lastfm.domain.port.TagGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopAlbumsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopArtistsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetTopTagsResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.List;
import java.util.Map;

public class TagGatewayImpl extends BaseGatewayImpl implements TagGateway {

  public TagGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
  }

  @Override
  public Tag getInfo(String tag, String lang) {
    Map<String, String> params =
        ParameterBuilder.create().putRequired("tag", tag).put("lang", lang).build();

    GetInfoResponse response =
        executeWithErrorHandling("tag.getinfo", params, GetInfoResponse.class);
    return TagMapper.from(response);
  }

  @Override
  public List<TagAlbum> getTopAlbums(String tag, Integer limit, Integer page) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("tag", tag)
            .put("limit", limit)
            .put("page", page)
            .build();

    GetTopAlbumsResponse response =
        executeWithErrorHandling("tag.gettopalbums", params, GetTopAlbumsResponse.class);
    return TagMapper.from(response);
  }

  @Override
  public List<TagArtist> getTopArtists(String tag, Integer limit, Integer page) {
    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("tag", tag)
            .put("limit", limit)
            .put("page", page)
            .build();

    GetTopArtistsResponse response =
        executeWithErrorHandling("tag.gettopartists", params, GetTopArtistsResponse.class);
    return TagMapper.from(response);
  }

  @Override
  public List<TopTag> getTopTags() {
    Map<String, String> params = ParameterBuilder.create().build();

    GetTopTagsResponse response =
        executeWithErrorHandling("tag.getTopTags", params, GetTopTagsResponse.class);
    return TagMapper.from(response);
  }
}

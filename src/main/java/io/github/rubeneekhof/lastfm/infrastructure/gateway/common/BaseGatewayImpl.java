package io.github.rubeneekhof.lastfm.infrastructure.gateway.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.LastFmApiGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.Map;

public abstract class BaseGatewayImpl implements LastFmApiGateway {

  protected final HttpExecutor http;
  protected final ObjectMapper mapper;

  protected BaseGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    this.http = http;
    this.mapper = mapper;
  }

  protected <T> T executeWithErrorHandling(
      String method, Map<String, String> params, Class<T> responseClass) {
    try {
      T response = getAndParse(http, mapper, method, params, responseClass);
      return response;
    } catch (LastFmException e) {
      throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
    }
  }
}

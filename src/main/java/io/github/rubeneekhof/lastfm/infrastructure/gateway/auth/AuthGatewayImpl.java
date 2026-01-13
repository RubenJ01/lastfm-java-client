package io.github.rubeneekhof.lastfm.infrastructure.gateway.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Session;
import io.github.rubeneekhof.lastfm.domain.port.AuthGateway;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.auth.response.GetSessionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.auth.response.GetTokenResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.ApiSignatureGenerator;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.util.Map;
import java.util.TreeMap;

public class AuthGatewayImpl extends BaseGatewayImpl implements AuthGateway {

  private final String apiSecret;

  public AuthGatewayImpl(HttpExecutor http, ObjectMapper mapper, String apiSecret) {
    super(http, mapper);
    this.apiSecret = apiSecret;
  }

  @Override
  public String getToken() {
    Map<String, String> sigParams = new TreeMap<>();
    sigParams.put("api_key", http.getApiKey());

    String apiSig = ApiSignatureGenerator.generate("auth.getToken", sigParams, apiSecret);

    Map<String, String> params = ParameterBuilder.create().putRequired("api_sig", apiSig).build();

    GetTokenResponse response =
        executeWithErrorHandling("auth.getToken", params, GetTokenResponse.class);
    return response.token;
  }

  @Override
  public Session getSession(String token) {
    Map<String, String> sigParams = new TreeMap<>();
    sigParams.put("api_key", http.getApiKey());
    sigParams.put("token", token);

    String apiSig = ApiSignatureGenerator.generate("auth.getSession", sigParams, apiSecret);

    Map<String, String> params =
        ParameterBuilder.create()
            .putRequired("token", token)
            .putRequired("api_sig", apiSig)
            .build();

    GetSessionResponse response =
        executeWithErrorHandling("auth.getSession", params, GetSessionResponse.class);
    return AuthMapper.from(response);
  }
}

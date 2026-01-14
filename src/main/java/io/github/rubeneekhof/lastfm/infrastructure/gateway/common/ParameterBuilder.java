package io.github.rubeneekhof.lastfm.infrastructure.gateway.common;

import java.util.HashMap;
import java.util.Map;

public final class ParameterBuilder {

  private final Map<String, String> params;

  private ParameterBuilder() {
    this.params = new HashMap<>();
  }

  public static ParameterBuilder create() {
    return new ParameterBuilder();
  }

  public ParameterBuilder put(String key, String value) {
    if (value != null && !value.isBlank()) {
      params.put(key, value);
    }
    return this;
  }

  public ParameterBuilder put(String key, Integer value) {
    if (value != null) {
      params.put(key, String.valueOf(value));
    }
    return this;
  }

  public ParameterBuilder put(String key, Boolean value) {
    if (value != null) {
      params.put(key, value ? "1" : "0");
    }
    return this;
  }

  public ParameterBuilder putRequired(String key, String value) {
    params.put(key, value);
    return this;
  }

  public Map<String, String> build() {
    return params;
  }
}

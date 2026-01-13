package io.github.rubeneekhof.lastfm.infrastructure.gateway;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseMapper {
  protected static <T> List<T> mapImages(
      List<? extends BaseImageResponse> images, Function<BaseImageResponse, T> mapper) {
    return Optional.ofNullable(images)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(mapper).toList())
        .orElse(List.of());
  }

  protected static int parseNumber(Object value) {
    if (value == null) {
      return 0;
    }

    if (value instanceof Number) {
      return ((Number) value).intValue();
    }

    if (value instanceof String str) {
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
}

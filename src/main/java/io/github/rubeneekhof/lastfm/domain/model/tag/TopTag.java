package io.github.rubeneekhof.lastfm.domain.model.tag;

public record TopTag(String name, long count, long reach) {
  @Override
  public String toString() {
    return "TopTag { name: '" + name + "', count: " + count + ", reach: " + reach + " }";
  }
}

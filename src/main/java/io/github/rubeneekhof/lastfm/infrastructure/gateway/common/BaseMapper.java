package io.github.rubeneekhof.lastfm.infrastructure.gateway.common;

import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseImageResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.response.BaseWikiResponse;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Base class for all API-to-domain mappers.
 *
 * <p>{@code BaseMapper} provides shared, reusable mapping utilities for converting Last.fm API
 * response models into domain objects. It centralizes common transformation logic.
 *
 * <p>The Last.fm API exposes many structurally identical components (such as images and wiki
 * sections) across different endpoints. This class avoids code duplication by providing generic,
 * function-based mapping helpers that can be reused by all concrete mappers (e.g. {@code
 * TagMapper}, {@code ArtistMapper}, {@code AlbumMapper}).
 */
public abstract class BaseMapper {

  /**
   * Maps a list of API image DTOs into a list of domain image objects.
   *
   * <p>This method abstracts the transformation of image metadata (size and URL) into any target
   * domain image type. It safely handles {@code null} and empty lists by returning an empty list.
   *
   * <p>To use this method, the response DTO's image type must implement {@link BaseImageResponse},
   * which exposes the common image fields across all Last.fm API responses.
   *
   * <p>Example DTO implementation:
   *
   * <pre>{@code
   * public static class Image implements BaseImageResponse {
   *     public String size;
   *
   *     @JsonProperty("#text")
   *     public String url;
   *
   *     @Override public String getSize() { return size; }
   *     @Override public String getUrl()  { return url; }
   * }
   * }</pre>
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * List<TagAlbum.Image> images = mapImages(
   *     response.album.image,
   *     img -> new TagAlbum.Image(img.getSize(), img.getUrl())
   * );
   * }</pre>
   *
   * @param images the list of API image DTOs (may be {@code null})
   * @param mapper a function that converts a {@link BaseImageResponse} into the target domain image
   *     type
   * @param <T> the target domain image type
   * @return a mapped list of images, or an empty list if {@code images} is {@code null} or empty
   */
  protected static <T> List<T> mapImages(
      List<? extends BaseImageResponse> images, Function<BaseImageResponse, T> mapper) {
    return Optional.ofNullable(images)
        .filter(list -> !list.isEmpty())
        .map(list -> list.stream().map(mapper).toList())
        .orElse(List.of());
  }

  /**
   * Maps a wiki section from an API response DTO into a domain wiki object.
   *
   * <p>This method abstracts the transformation of wiki metadata (published date, summary, and
   * content) into any target domain type. If the API wiki object is {@code null}, this method
   * returns {@code null}.
   *
   * <p>To use this method, the response DTO's wiki type must implement {@link BaseWikiResponse},
   * which exposes the common wiki fields across all Last.fm API responses.
   *
   * <p>Example DTO implementation:
   *
   * <pre>{@code
   * public static class Wiki implements BaseWikiResponse {
   *     public String published;
   *     public String summary;
   *     public String content;
   *
   *     @Override public String getPublished() { return published; }
   *     @Override public String getSummary()   { return summary; }
   *     @Override public String getContent()   { return content; }
   * }
   * }</pre>
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * Tag.Wiki wiki = mapWiki(
   *     response.tag.wiki,
   *     w -> new Tag.Wiki(w.getPublished(), w.getSummary(), w.getContent())
   * );
   * }</pre>
   *
   * @param wiki the API wiki DTO (may be {@code null})
   * @param mapper a function that converts a {@link BaseWikiResponse} into the target domain wiki
   *     type
   * @param <T> the target domain wiki type
   * @return the mapped wiki object, or {@code null} if {@code wiki} is {@code null}
   */
  protected static <T> T mapWiki(BaseWikiResponse wiki, Function<BaseWikiResponse, T> mapper) {
    if (wiki == null) {
      return null;
    }
    return mapper.apply(wiki);
  }

  /**
   * Parses a numeric value from an API response into an {@code int}.
   *
   * <p>The Last.fm API often returns numbers as strings or mixed numeric types. This method safely
   * handles {@code null}, {@link Number}, and {@link String} inputs. If the value cannot be parsed,
   * {@code 0} is returned.
   *
   * <p>Supported inputs:
   *
   * <ul>
   *   <li>{@link Number} (e.g., {@link Integer}, {@link Long}, {@link Double})
   *   <li>{@link String} containing an integer or decimal representation
   * </ul>
   *
   * @param value the value to parse (may be {@code null})
   * @return the parsed integer value, or {@code 0} if parsing fails
   */
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

  /**
   * Parses a string value from an API response into a {@code long}.
   *
   * <p>The Last.fm API often returns numbers as strings. This method safely handles {@code null} and
   * blank strings. If the value cannot be parsed, {@code 0} is returned.
   *
   * @param value the string value to parse (may be {@code null})
   * @return the parsed long value, or {@code 0} if parsing fails or the value is {@code null} or blank
   */
  protected static long parseLong(String value) {
    if (value == null || value.isBlank()) {
      return 0;
    }
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}

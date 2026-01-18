package io.github.rubeneekhof.lastfm.util;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Utility class for working with Unix timestamps (seconds since epoch, UTC).
 *
 * <p>This class provides convenient methods to create and manipulate Unix timestamps for use with
 * Last.fm API endpoints. All timestamps are in UTC and represent seconds since January 1, 1970,
 * 00:00:00 UTC.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Current time
 * long now = UnixTime.now();
 *
 * // From ISO-8601 date string
 * long timestamp = UnixTime.at("2026-01-01");
 *
 * // From LocalDate
 * long timestamp = UnixTime.of(LocalDate.of(2026, 1, 1));
 *
 * // Pass through existing timestamp
 * long timestamp = UnixTime.of(1700000000L);
 *
 * // Relative time
 * long weekAgo = UnixTime.daysAgo(7);
 *
 * // Time ranges
 * UnixTime.Range lastWeek = UnixTime.lastDays(7);
 * getWeeklyAlbumChart("RubenJ01", lastWeek.from(), lastWeek.to());
 * }</pre>
 */
public final class UnixTime {

  private static final DateTimeFormatter ISO_DATE_FORMATTER =
      DateTimeFormatter.ISO_LOCAL_DATE;

  private UnixTime() {
  }

  /**
   * Returns the current time as a Unix timestamp in seconds (UTC).
   *
   * @return the current Unix timestamp in seconds
   */
  public static long now() {
    return java.time.Instant.now().getEpochSecond();
  }

  /**
   * Converts an ISO-8601 date string (yyyy-MM-dd) to a Unix timestamp at the start of day UTC.
   *
   * @param isoDate the date string in ISO-8601 format (yyyy-MM-dd), must not be null or blank
   * @return the Unix timestamp in seconds for the start of the specified day in UTC
   * @throws IllegalArgumentException if the date string is null, blank, or not in valid ISO-8601
   *     format
   */
  public static long at(String isoDate) {
    if (isoDate == null || isoDate.isBlank()) {
      throw new IllegalArgumentException("Date string must not be null or blank");
    }
    try {
      LocalDate date = LocalDate.parse(isoDate, ISO_DATE_FORMATTER);
      return of(date);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "Invalid date format. Expected ISO-8601 format (yyyy-MM-dd): " + isoDate, e);
    }
  }

  /**
   * Converts a {@link LocalDate} to a Unix timestamp at the start of day UTC.
   *
   * @param date the date to convert, must not be null
   * @return the Unix timestamp in seconds for the start of the specified day in UTC
   * @throws IllegalArgumentException if the date is null
   */
  public static long of(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date must not be null");
    }
    return date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
  }

  /**
   * Passes through a manually provided Unix timestamp. This method validates that the timestamp is
   * non-negative and returns it unchanged.
   *
   * @param unixSeconds the Unix timestamp in seconds, must be non-negative
   * @return the same Unix timestamp
   * @throws IllegalArgumentException if the timestamp is negative
   */
  public static long of(long unixSeconds) {
    if (unixSeconds < 0) {
      throw new IllegalArgumentException(
          "Unix timestamp must be non-negative, got: " + unixSeconds);
    }
    return unixSeconds;
  }

  /**
   * Returns a Unix timestamp for a time that is the specified number of days ago from now (UTC).
   *
   * @param days the number of days ago, must be non-negative
   * @return the Unix timestamp in seconds
   * @throws IllegalArgumentException if days is negative
   */
  public static long daysAgo(int days) {
    if (days < 0) {
      throw new IllegalArgumentException("Days must be non-negative, got: " + days);
    }
    return now() - (days * 86400L);
  }

  /**
   * Represents an immutable time range with a start (from) and end (to) timestamp.
   *
   * @param from the start timestamp (inclusive)
   * @param to the end timestamp (inclusive)
   */
  public record Range(long from, long to) {
    /**
     * Creates a range from two timestamps.
     *
     * @param from the start timestamp (inclusive)
     * @param to the end timestamp (inclusive)
     * @return a new Range
     * @throws IllegalArgumentException if from is greater than to, or if either timestamp is
     *     negative
     */
    public static Range of(long from, long to) {
      if (from < 0 || to < 0) {
        throw new IllegalArgumentException(
            "Timestamps must be non-negative: from=" + from + ", to=" + to);
      }
      if (from > to) {
        throw new IllegalArgumentException(
            "Start timestamp must not be greater than end timestamp: from=" + from + ", to=" + to);
      }
      return new Range(from, to);
    }
  }

  /**
   * Returns a time range for the last N days ending at now (UTC). The range starts N days ago and
   * ends at the current time.
   *
   * @param days the number of days to look back, must be positive
   * @return a Range with from = (now - days) and to = now
   * @throws IllegalArgumentException if days is not positive
   */
  public static Range lastDays(int days) {
    if (days <= 0) {
      throw new IllegalArgumentException("Days must be positive, got: " + days);
    }
    long to = now();
    long from = to - (days * 86400L);
    return new Range(from, to);
  }
}

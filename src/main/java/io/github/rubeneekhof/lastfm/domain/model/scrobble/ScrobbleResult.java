package io.github.rubeneekhof.lastfm.domain.model.scrobble;

public record ScrobbleResult(
    String track,
    String artist,
    String album,
    String albumArtist,
    long timestamp,
    boolean trackCorrected,
    boolean artistCorrected,
    boolean albumCorrected,
    boolean albumArtistCorrected,
    int ignoredMessageCode) {

  public boolean wasIgnored() {
    return ignoredMessageCode != 0;
  }
}

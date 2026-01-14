package io.github.rubeneekhof.lastfm.infrastructure.gateway.track;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.scrobble.Scrobble;
import io.github.rubeneekhof.lastfm.domain.model.ScrobbleResponse;
import io.github.rubeneekhof.lastfm.domain.model.Track;
import io.github.rubeneekhof.lastfm.domain.model.error.LastFmFailureException;
import io.github.rubeneekhof.lastfm.domain.port.TrackGateway;
import io.github.rubeneekhof.lastfm.exception.LastFmException;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.BaseGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.LastFmErrorMapper;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.common.ParameterBuilder;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.http.ApiSignatureGenerator;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TrackGatewayImpl extends BaseGatewayImpl implements TrackGateway {

  private final String apiSecret;
  private final String sessionKey;

  public TrackGatewayImpl(HttpExecutor http, ObjectMapper mapper) {
    super(http, mapper);
    this.apiSecret = null;
    this.sessionKey = null;
  }

  public TrackGatewayImpl(
      HttpExecutor http, ObjectMapper mapper, String apiSecret, String sessionKey) {
    super(http, mapper);
    this.apiSecret = apiSecret;
    this.sessionKey = sessionKey;
  }

  @Override
  public ScrobbleResponse scrobble(List<Scrobble> scrobbles) {
    if (apiSecret == null || sessionKey == null) {
      throw new IllegalStateException(
          "Scrobbling requires authentication. Use createAuthenticated() to create a client with session key.");
    }

    try {
      Map<String, String> params = buildScrobbleParams(scrobbles);
      params.put("sk", sessionKey);

      Map<String, String> sigParams = new TreeMap<>(params);
      sigParams.put("api_key", http.getApiKey());

      String apiSig = ApiSignatureGenerator.generate("track.scrobble", sigParams, apiSecret);
      params.put("api_sig", apiSig);

      String body = http.post("track.scrobble", params);
      io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response.ScrobbleResponse response =
          mapper.readValue(
              body,
              io.github.rubeneekhof.lastfm.infrastructure.gateway.track.response.ScrobbleResponse
                  .class);
      return TrackMapper.from(response);
    } catch (LastFmException e) {
      throw new LastFmFailureException(LastFmErrorMapper.map(e.code(), e.getMessage()));
    } catch (IOException | InterruptedException e) {
      throw new LastFmException(0, "Failed to scrobble tracks: " + e.getMessage(), e);
    }
  }

  private Map<String, String> buildScrobbleParams(List<Scrobble> scrobbles) {
    Map<String, String> params = new TreeMap<>();

    if (scrobbles.size() == 1) {
      Scrobble scrobble = scrobbles.get(0);
      addScrobbleParams(params, scrobble, null);
    } else {
      for (int i = 0; i < scrobbles.size(); i++) {
        addScrobbleParams(params, scrobbles.get(i), i);
      }
    }

    return params;
  }

  private void addScrobbleParams(Map<String, String> params, Scrobble scrobble, Integer index) {
    String artistKey = index != null ? "artist[" + index + "]" : "artist";
    String trackKey = index != null ? "track[" + index + "]" : "track";
    String timestampKey = index != null ? "timestamp[" + index + "]" : "timestamp";
    String albumKey = index != null ? "album[" + index + "]" : "album";
    String albumArtistKey = index != null ? "albumArtist[" + index + "]" : "albumArtist";
    String mbidKey = index != null ? "mbid[" + index + "]" : "mbid";
    String trackNumberKey = index != null ? "trackNumber[" + index + "]" : "trackNumber";
    String durationKey = index != null ? "duration[" + index + "]" : "duration";
    String chosenByUserKey = index != null ? "chosenByUser[" + index + "]" : "chosenByUser";
    String contextKey = index != null ? "context[" + index + "]" : "context";
    String streamIdKey = index != null ? "streamId[" + index + "]" : "streamId";

    params.put(artistKey, scrobble.artist());
    params.put(trackKey, scrobble.track());
    params.put(timestampKey, String.valueOf(scrobble.timestamp()));

    if (scrobble.album() != null && !scrobble.album().isBlank()) {
      params.put(albumKey, scrobble.album());
    }
    if (scrobble.albumArtist() != null && !scrobble.albumArtist().isBlank()) {
      params.put(albumArtistKey, scrobble.albumArtist());
    }
    if (scrobble.mbid() != null && !scrobble.mbid().isBlank()) {
      params.put(mbidKey, scrobble.mbid());
    }
    if (scrobble.trackNumber() != null) {
      params.put(trackNumberKey, String.valueOf(scrobble.trackNumber()));
    }
    if (scrobble.duration() != null) {
      params.put(durationKey, String.valueOf(scrobble.duration()));
    }
    if (scrobble.chosenByUser() != null) {
      params.put(chosenByUserKey, scrobble.chosenByUser() ? "1" : "0");
    }
    if (scrobble.context() != null && !scrobble.context().isBlank()) {
      params.put(contextKey, scrobble.context());
    }
    if (scrobble.streamId() != null && !scrobble.streamId().isBlank()) {
      params.put(streamIdKey, scrobble.streamId());
    }
  }

  @Override
  public Track getInfo(
      String artist, String track, String mbid, Boolean autocorrect, String username) {
    Map<String, String> params =
        ParameterBuilder.create()
            .put("artist", artist)
            .put("track", track)
            .put("mbid", mbid)
            .put("autocorrect", autocorrect)
            .put("username", username)
            .build();

    GetInfoResponse response =
        executeWithErrorHandling("track.getInfo", params, GetInfoResponse.class);
    return TrackMapper.from(response);
  }
}

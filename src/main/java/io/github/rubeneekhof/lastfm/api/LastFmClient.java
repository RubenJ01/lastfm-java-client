package io.github.rubeneekhof.lastfm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.application.album.AlbumService;
import io.github.rubeneekhof.lastfm.application.artist.ArtistService;
import io.github.rubeneekhof.lastfm.application.auth.AuthService;
import io.github.rubeneekhof.lastfm.application.chart.ChartService;
import io.github.rubeneekhof.lastfm.application.geo.GeoService;
import io.github.rubeneekhof.lastfm.application.library.LibraryService;
import io.github.rubeneekhof.lastfm.application.tag.TagService;
import io.github.rubeneekhof.lastfm.application.track.TrackService;
import io.github.rubeneekhof.lastfm.application.user.UserService;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.album.AlbumGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.ArtistGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.auth.AuthGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.ChartGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.geo.GeoGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.library.LibraryGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.TagGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.track.TrackGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.UserGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;
import java.net.http.HttpClient;

/**
 * Main client for interacting with the Last.fm API. Use the builder pattern to create configured
 * instances:
 *
 * <pre>{@code
 * LastFmClient client = LastFmClient.builder()
 *     .apiKey("your-api-key")
 *     .build();
 * }</pre>
 */
public class LastFmClient {

  private final ArtistService artistService;
  private final AlbumService albumService;
  private final AuthService authService;
  private final ChartService chartService;
  private final GeoService geoService;
  private final LibraryService libraryService;
  private final TagService tagService;
  private final TrackService trackService;
  private final UserService userService;

  private LastFmClient(
      ArtistService artistService,
      AlbumService albumService,
      AuthService authService,
      ChartService chartService,
      GeoService geoService,
      LibraryService libraryService,
      TagService tagService,
      TrackService trackService,
      UserService userService) {
    this.artistService = artistService;
    this.albumService = albumService;
    this.authService = authService;
    this.chartService = chartService;
    this.geoService = geoService;
    this.libraryService = libraryService;
    this.tagService = tagService;
    this.trackService = trackService;
    this.userService = userService;
  }

  /**
   * Creates a new builder for constructing a LastFmClient with custom configuration.
   *
   * @return a new Builder instance
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Creates a LastFmClient from a ClientConfig. This is used internally by the builder.
   *
   * @param config the client configuration
   * @return a configured LastFmClient instance
   */
  static LastFmClient createFromConfig(ClientConfig config) {
    HttpExecutor http =
        new HttpExecutor(
            config.apiKey(),
            config.objectMapper(),
            config.baseUrl(),
            config.httpClient(),
            config.userAgent());

    ArtistService artistService = new ArtistService(new ArtistGatewayImpl(http, config.objectMapper()));
    AlbumService albumService = new AlbumService(new AlbumGatewayImpl(http, config.objectMapper()));
    ChartService chartService = new ChartService(new ChartGatewayImpl(http, config.objectMapper()));
    GeoService geoService = new GeoService(new GeoGatewayImpl(http, config.objectMapper()));
    LibraryService libraryService = new LibraryService(new LibraryGatewayImpl(http, config.objectMapper()));
    TagService tagService = new TagService(new TagGatewayImpl(http, config.objectMapper()));
    TrackService trackService = new TrackService(new TrackGatewayImpl(http, config.objectMapper()));
    UserService userService = new UserService(new UserGatewayImpl(http, config.objectMapper()));

    AuthService authService = null;
    if (config.apiSecret() != null) {
      authService = new AuthService(new AuthGatewayImpl(http, config.objectMapper(), config.apiSecret()));
    }

    // If session key is provided, use authenticated track service
    if (config.sessionKey() != null && config.apiSecret() != null) {
      trackService =
          new TrackService(
              new TrackGatewayImpl(
                  http, config.objectMapper(), config.apiSecret(), config.sessionKey()));
    }

    return new LastFmClient(
        artistService,
        albumService,
        authService,
        chartService,
        geoService,
        libraryService,
        tagService,
        trackService,
        userService);
  }


  public ArtistService artists() {
    return artistService;
  }

  public AlbumService albums() {
    return albumService;
  }

  public AuthService auth() {
    return authService;
  }

  public ChartService charts() {
    return chartService;
  }

  public GeoService geo() {
    return geoService;
  }

  public LibraryService library() {
    return libraryService;
  }

  public TagService tags() {
    return tagService;
  }

  public TrackService tracks() {
    return trackService;
  }

  public UserService users() {
    return userService;
  }

  /**
   * Builder for creating LastFmClient instances with custom configuration. Provides a fluent API for
   * configuring all aspects of the client.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * LastFmClient client = LastFmClient.builder()
   *     .apiKey("your-api-key")
   *     .userAgent("my-app/1.0")
   *     .baseUrl("https://ws.audioscrobbler.com/2.0/")
   *     .httpClient(customHttpClient)
   *     .objectMapper(customMapper)
   *     .build();
   * }</pre>
   */
  public static final class Builder {

    private static final String DEFAULT_BASE_URL = "https://ws.audioscrobbler.com/2.0/";
    private static final String DEFAULT_USER_AGENT = "lastfm-java-client/1.0.0";

    private String apiKey;
    private String apiSecret;
    private String sessionKey;
    private String baseUrl;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private String userAgent;

    private Builder() {
      // Private constructor, only LastFmClient.builder() can create builders
    }

    /**
     * Sets the Last.fm API key. This is required.
     *
     * @param apiKey the API key
     * @return this builder
     */
    public Builder apiKey(String apiKey) {
      this.apiKey = apiKey;
      return this;
    }

    /**
     * Sets the Last.fm API secret. Required for authenticated operations.
     *
     * @param apiSecret the API secret
     * @return this builder
     */
    public Builder apiSecret(String apiSecret) {
      this.apiSecret = apiSecret;
      return this;
    }

    /**
     * Sets the authenticated session key. Required for authenticated operations.
     *
     * @param sessionKey the session key
     * @return this builder
     */
    public Builder sessionKey(String sessionKey) {
      this.sessionKey = sessionKey;
      return this;
    }

    /**
     * Sets the base URL for API requests. Defaults to "https://ws.audioscrobbler.com/2.0/".
     *
     * @param baseUrl the base URL
     * @return this builder
     */
    public Builder baseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    /**
     * Sets the HTTP client to use for requests. Defaults to a new HttpClient with default settings.
     *
     * @param httpClient the HTTP client
     * @return this builder
     */
    public Builder httpClient(HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    /**
     * Sets the ObjectMapper for JSON serialization/deserialization. Defaults to a new ObjectMapper
     * with default settings.
     *
     * @param objectMapper the ObjectMapper
     * @return this builder
     */
    public Builder objectMapper(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
      return this;
    }

    /**
     * Sets the User-Agent header value. Defaults to "lastfm-java-client/1.0.0".
     *
     * @param userAgent the user agent string
     * @return this builder
     */
    public Builder userAgent(String userAgent) {
      this.userAgent = userAgent;
      return this;
    }

    /**
     * Builds the LastFmClient with the configured settings. Validates that required fields are
     * present.
     *
     * @return a configured LastFmClient instance
     * @throws IllegalStateException if required fields are missing
     */
    public LastFmClient build() {
      validate();
      ClientConfig config = buildConfig();
      return LastFmClient.createFromConfig(config);
    }

    private void validate() {
      if (apiKey == null || apiKey.isBlank()) {
        throw new IllegalStateException("API key is required");
      }
    }

    private ClientConfig buildConfig() {
      return new ClientConfig(
          apiKey,
          apiSecret,
          sessionKey,
          baseUrl != null ? baseUrl : DEFAULT_BASE_URL,
          httpClient != null ? httpClient : HttpClient.newHttpClient(),
          objectMapper != null ? objectMapper : new ObjectMapper(),
          userAgent != null ? userAgent : DEFAULT_USER_AGENT);
    }
  }
}

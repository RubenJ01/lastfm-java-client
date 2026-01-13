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

  public static LastFmClient create(String apiKey) {
    return create(apiKey, null);
  }

  public static LastFmClient create(String apiKey, String apiSecret) {
    ObjectMapper mapper = new ObjectMapper();
    HttpExecutor http = new HttpExecutor(apiKey, mapper);

    ArtistService artistService = new ArtistService(new ArtistGatewayImpl(http, mapper));
    AlbumService albumService = new AlbumService(new AlbumGatewayImpl(http, mapper));
    ChartService chartService = new ChartService(new ChartGatewayImpl(http, mapper));
    GeoService geoService = new GeoService(new GeoGatewayImpl(http, mapper));
    LibraryService libraryService = new LibraryService(new LibraryGatewayImpl(http, mapper));
    TagService tagService = new TagService(new TagGatewayImpl(http, mapper));
    TrackService trackService = new TrackService(new TrackGatewayImpl(http, mapper));
    UserService userService = new UserService(new UserGatewayImpl(http, mapper));

    AuthService authService = null;
    if (apiSecret != null) {
      authService = new AuthService(new AuthGatewayImpl(http, mapper, apiSecret));
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

  public static LastFmClient createAuthenticated(
      String apiKey, String apiSecret, String sessionKey) {
    ObjectMapper mapper = new ObjectMapper();
    HttpExecutor http = new HttpExecutor(apiKey, mapper);

    ArtistService artistService = new ArtistService(new ArtistGatewayImpl(http, mapper));
    AlbumService albumService = new AlbumService(new AlbumGatewayImpl(http, mapper));
    ChartService chartService = new ChartService(new ChartGatewayImpl(http, mapper));
    GeoService geoService = new GeoService(new GeoGatewayImpl(http, mapper));
    LibraryService libraryService = new LibraryService(new LibraryGatewayImpl(http, mapper));
    TagService tagService = new TagService(new TagGatewayImpl(http, mapper));
    AuthService authService = new AuthService(new AuthGatewayImpl(http, mapper, apiSecret));
    TrackService trackService =
        new TrackService(new TrackGatewayImpl(http, mapper, apiSecret, sessionKey));
    UserService userService = new UserService(new UserGatewayImpl(http, mapper));

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
}

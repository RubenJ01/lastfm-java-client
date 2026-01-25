# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [2.0.0] - 2026-01-25

### Added
- Builder pattern for `LastFmClient` configuration
  - `LastFmClient.builder()` method for creating configured client instances
  - Support for custom `HttpClient` configuration (timeouts, proxy, connection pool)
  - Support for custom `ObjectMapper` configuration
  - Configurable base URL (defaults to `https://ws.audioscrobbler.com/2.0/`)
  - Configurable User-Agent header (defaults to `lastfm-java-client/1.0.0`)
  - `ClientConfig` record for immutable configuration
  - `LastFmClient.Builder` nested class with fluent API
- Pagination helper utilities for working with paginated endpoints
  - `Page<T>` abstraction for paginated results with metadata
  - `PaginationIterator` for automatically iterating over all pages
  - `PaginationHelper` utility class with convenience methods
  - `ArtistSearchPagination` for artist search pagination
  - Support for iterating, streaming, and collecting paginated results
  - Optional limits (max pages or max items)

### Changed
- `HttpExecutor` now accepts configurable `HttpClient`, `baseUrl`, and `userAgent` parameters
- Client construction now uses builder pattern as the primary method
- **BREAKING**: Static factory methods removed in favor of builder pattern

### Removed
- `LastFmClient.create(String apiKey)` static factory method
- `LastFmClient.create(String apiKey, String apiSecret)` static factory method
- `LastFmClient.createAuthenticated(String apiKey, String apiSecret, String sessionKey)` static factory method

## [1.0.0] - Initial Release

### Added

#### Core Client
- `LastFmClient` main entry point for API interactions
- Layered architecture with clean separation of concerns
- Domain layer that is pure and dependency-free
- Infrastructure layer for HTTP, JSON, and configuration

#### HTTP & Retry Support
- `HttpExecutor` for handling HTTP requests to Last.fm API
- `RetryExecutor` with configurable retry policies
- `RetryPolicy` with exponential backoff for transient failures
- Automatic retry on rate limit errors (HTTP 429) and service unavailability (HTTP 503)

#### Authentication
- `AuthService` for Last.fm authentication flow
- `getToken()` method to obtain authentication token
- `getAuthorizationUrl()` method to generate user authorization URL
- `getSession()` method to exchange token for session key
- Support for authenticated operations (scrobbling, loving tracks)

#### Artist Endpoints
- `ArtistService` with full artist API support
- `getInfo()` - Get artist information with optional language and autocorrect
- `getSimilar()` - Get similar artists with pagination support
- `getCorrection()` - Get artist name correction
- `search()` - Search for artists with pagination
- `ArtistGetInfoRequest` builder for complex artist info requests
- `ArtistGetSimilarRequest` builder for similar artist requests
- `ArtistSearchRequest` builder for artist search requests

#### Album Endpoints
- `AlbumService` for album information retrieval
- `getInfo()` - Get album information with optional language, autocorrect, and username
- `AlbumGetInfoRequest` builder for complex album info requests

#### Track Endpoints
- `TrackService` for track operations
- `getInfo()` - Get track information with optional autocorrect and username
- `scrobble()` - Scrobble single or multiple tracks
- `love()` - Mark a track as loved
- `unlove()` - Remove loved status from a track
- `TrackGetInfoRequest` builder for complex track info requests
- `TrackScrobbleRequest` builder for batch scrobbling
- `TrackLoveRequest` builder for track love operations
- `TrackUnloveRequest` builder for track unlove operations
- `Scrobble` domain model with timestamp support
- `ScrobbleResponse` for scrobbling results

#### User Endpoints
- `UserService` for user information
- `getInfo()` - Get user profile information
- `getWeeklyAlbumChart()` - Get user's weekly album chart with various overloads
- `UserGetWeeklyAlbumChartRequest` builder for chart requests

#### Chart Endpoints
- `ChartService` for chart data
- `getTopArtists()` - Get top artists chart with pagination
- `ChartGetTopArtistsRequest` builder for chart requests

#### Tag Endpoints
- `TagService` for tag-related operations
- `getInfo()` - Get tag information with optional language
- `getTopAlbums()` - Get top albums for a tag with pagination
- `getTopArtists()` - Get top artists for a tag with pagination
- `getTopTags()` - Get top tags globally
- `TagGetInfoRequest` builder for tag info requests
- `TagGetTopAlbumsRequest` builder for tag album requests
- `TagGetTopArtistsRequest` builder for tag artist requests

#### Geo Endpoints
- `GeoService` for geographic data
- `getTopArtists()` - Get top artists by country with pagination
- `GeoGetTopArtistsRequest` builder for geo requests

#### Library Endpoints
- `LibraryService` for user library operations
- `getArtists()` - Get user's library artists with pagination
- `LibraryGetArtistsRequest` builder for library requests

#### Domain Models
- `Artist` - Artist information model
- `Album` - Album information model
- `Track` - Track information model
- `User` - User profile model
- `Tag` - Tag information model
- `TagAlbum` - Tag album model
- `TagArtist` - Tag artist model
- `TopTag` - Top tag model
- `LibraryArtist` - Library artist model
- `WeeklyAlbumChart` - Weekly album chart model
- `Session` - Authentication session model
- `Scrobble` - Scrobble data model
- `ScrobbleResponse` - Scrobble response model

#### Utilities
- `UnixTime` utility class for timestamp conversions
- Helper methods for working with Last.fm timestamps

#### Error Handling
- `LastFmException` for API errors with error codes and messages
- `LastFmFailure` sealed interface for domain-level error representation
- Comprehensive error handling throughout the client

#### Documentation
- Comprehensive JavaDoc for all public APIs
- Wiki documentation with usage examples
- README with quick start guide
- API reference documentation

### Technical Details
- Java 17+ support
- Jackson for JSON serialization/deserialization
- `java.net.http.HttpClient` for HTTP communication
- Maven build configuration
- MIT License

[Unreleased]: https://github.com/RubenJ01/lastfm-java-client/compare/v2.0.0...HEAD
[2.0.0]: https://github.com/RubenJ01/lastfm-java-client/compare/v1.0.0...v2.0.0
[1.0.0]: https://github.com/RubenJ01/lastfm-java-client/releases/tag/v1.0.0

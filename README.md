# LastFM Java Client

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.9+-orange)
![Lines of Code](https://raw.githubusercontent.com/RubenJ01/lastfm-java-client/badges/badge.svg)

Create a client and start making requests:

````java
import io.github.rubeneekhof.lastfm.api.LastFmClient;

var apiKey = "your-api-key-here";
LastFmClient client = LastFmClient.create(apiKey);
````

## Basic Usage

Most endpoints work the same way - you have three options depending on how many parameters you need:

**Just the basics:**
````java
var artist = client.artists().getInfo("Cher");
var similar = client.artists().getSimilar("Cher");
````

**One or two extra options:**
````java
var artist = client.artists().getInfo("Cher", "en");
var artist = client.artists().getInfo("Cher", true);
var similar = client.artists().getSimilar("Cher", 10);
var similar = client.artists().getSimilar("Cher", false, 20);
````

**Everything else (including MBID):**
````java
import io.github.rubeneekhof.lastfm.application.ArtistGetInfoRequest;

var artist = client.artists().getInfo(
    ArtistGetInfoRequest.artist("Cher")
        .lang("en")
        .autocorrect(true)
        .username("myusername")
        .build()
);

// or using MBID
var artist = client.artists().getInfo(
    ArtistGetInfoRequest.mbid("bfcc6d75-a6a5-4bc6-8282-47aec8531818")
        .build()
);
````

## How the Builder Pattern Works

Each endpoint has its own request builder class. You start by calling either `artist("name")` or `mbid("id")` depending on what you want to search by. This is required - you must provide either an artist name or MBID.

Then you chain optional parameters before calling `build()`:

````java
ArtistGetInfoRequest.artist("Cher")    // required: start here
    .lang("en")                        // optional: biography language (ISO 639 code)
    .autocorrect(true)                 // optional: auto-fix typos (defaults vary by endpoint)
    .username("myusername")            // optional: include user-specific data
    .build()                           // required: creates the request object
````

The builder validates that you've provided either an artist name or MBID when you call `build()`. If you forget both, it'll throw an exception. All the chained methods return the builder itself, so you can call them in any order and skip any optional parameters you don't need.

For example, if you only need the language and nothing else:

````java
var artist = client.artists().getInfo(
    ArtistGetInfoRequest.artist("Cher")
        .lang("en")
        .build()
);
````

Same pattern applies to all endpoints. When we add albums or tracks, they'll have their own builders like `AlbumGetInfoRequest`, `TrackGetInfoRequest`, etc.

## Authentication

Some Last.fm API endpoints require authentication (like `track.scrobble`). The authentication process involves three steps:

### Step 1: Get a Token

First, create a client with your API key and secret, then request a token:

````java
String apiKey = "your-api-key";
String apiSecret = "your-api-secret";

LastFmClient client = LastFmClient.create(apiKey, apiSecret);
String token = client.auth().getToken();
````

### Step 2: Authorize the Token

Generate the authorization URL and visit it in your browser to grant permission:

````java
String authUrl = client.auth().getAuthorizationUrl(apiKey, token);
System.out.println("Visit this URL to authorize: " + authUrl);
````

After granting permission, you'll see a success page. **Important:** Tokens are valid for 60 minutes and can only be used once.

### Step 3: Get a Session Key

Once the token is authorized, exchange it for a session key:

````java
Session session = client.auth().getSession(token);
String sessionKey = session.key();
````

**Session keys don't expire!** Save your session key and reuse it for all future authenticated requests. You only need to go through the token authorization process once.

### Using Authenticated Endpoints

Once you have a session key, create an authenticated client:

````java
LastFmClient authenticatedClient = LastFmClient.createAuthenticated(
    apiKey, 
    apiSecret, 
    sessionKey
);
````

This client can now use authenticated endpoints like `track.scrobble`.

## Scrobbling

Scrobbling is the process of recording that you've listened to a track. The `track.scrobble` endpoint allows you to submit scrobbles to Last.fm.

### Basic Scrobbling

The minimum required information is artist, track, and timestamp:

````java
import io.github.rubeneekhof.lastfm.domain.model.scrobble.Scrobble;
import io.github.rubeneekhof.lastfm.util.UnixTime;

// Create an authenticated client first
LastFmClient client = LastFmClient.createAuthenticated(apiKey, apiSecret, sessionKey);

// Scrobble a single track (1 minute ago)
Scrobble scrobble = Scrobble.builder()
        .artist("Radiohead")
        .track("Creep")
        .timestamp(UnixTime.now() - 60) // 1 minute ago
        .build();

ScrobbleResponse response = client.tracks().scrobble(scrobble);
System.out.println("Accepted: " + response.accepted() +", Ignored: "+response.ignored());
````

### Scrobbling with Optional Parameters

You can include additional information like album, track number, duration, etc.:

````java
import io.github.rubeneekhof.lastfm.util.UnixTime;

Scrobble scrobble = Scrobble.builder()
    .artist("The Beatles")
    .track("Hey Jude")
    .timestamp(UnixTime.daysAgo(1)) // 1 day ago
    .album("The Beatles 1967-1970")
    .albumArtist("The Beatles")
    .mbid("b10bbbfc-cf9e-42e0-be17-e2c3e1d2600d")
    .trackNumber(1)
    .duration(431) // seconds
    .chosenByUser(true)
    .build();

ScrobbleResponse response = client.tracks().scrobble(scrobble);
````

### Batch Scrobbling

You can scrobble up to 50 tracks in a single request:

````java
import io.github.rubeneekhof.lastfm.application.track.TrackScrobbleRequest;

Scrobble scrobble1 = Scrobble.builder()
    .artist("Daft Punk")
    .track("One More Time")
    .timestamp(baseTimestamp)
    .album("Discovery")
    .build();

Scrobble scrobble2 = Scrobble.builder()
    .artist("Daft Punk")
    .track("Harder, Better, Faster, Stronger")
    .timestamp(baseTimestamp - 60)
    .album("Discovery")
    .build();

TrackScrobbleRequest batch = TrackScrobbleRequest.builder()
    .addScrobble(scrobble1)
    .addScrobble(scrobble2)
    .build();

ScrobbleResponse response = client.tracks().scrobble(batch);
````

### Important Notes About Scrobbling

- **Timestamps**: Must be in UNIX timestamp format (seconds since epoch). Timestamps that are too old (more than ~14 days) or too new (in the future) will be ignored. Use the `UnixTime` utility class for ergonomic timestamp handling (see below).
- **Accepted vs Ignored**: The response includes counts of accepted and ignored scrobbles. Check `response.results()` to see details about each scrobble, including ignored message codes.
- **Rate Limits**: Be mindful of Last.fm's rate limits when scrobbling multiple tracks.
- **Session Key**: You must use an authenticated client with a valid session key. Session keys don't expire, so save yours after the first authentication.

## Unix Timestamp Utilities

The `UnixTime` utility class provides ergonomic methods for working with Unix timestamps (seconds since epoch, UTC). It's particularly useful for endpoints that require timestamps like `track.scrobble` and `user.getWeeklyAlbumChart`.

### Basic Usage

````java
import io.github.rubeneekhof.lastfm.util.UnixTime;

// Get current timestamp
long now = UnixTime.now();

// Convert ISO-8601 date string (yyyy-MM-dd) to timestamp
long timestamp = UnixTime.at("2024-01-15");

// Convert LocalDate to timestamp
long timestamp = UnixTime.of(LocalDate.of(2024, 1, 15));

// Get timestamp for N days ago
long oneWeekAgo = UnixTime.daysAgo(7);

// Create a date range (useful for weekly charts)
UnixTime.Range lastWeek = UnixTime.lastDays(7);
````

### Using with Scrobbling

````java
// Scrobble a track from 5 minutes ago
Scrobble scrobble = Scrobble.builder()
    .artist("Radiohead")
    .track("Creep")
    .timestamp(UnixTime.now() - 300) // 5 minutes ago
    .build();

// Or scrobble from a specific date
Scrobble scrobble = Scrobble.builder()
    .artist("The Beatles")
    .track("Hey Jude")
    .timestamp(UnixTime.at("2024-01-15"))
    .build();
````

### Using with Weekly Charts

````java
// Get last 7 days chart
var range = UnixTime.lastDays(7);
WeeklyAlbumChart chart = client.users().getWeeklyAlbumChart("RubenJ01", range.from(), range.to());

// Or with specific dates
long from = UnixTime.at("2024-01-15");
long to = UnixTime.at("2024-01-22");
WeeklyAlbumChart chart = client.users().getWeeklyAlbumChart("RubenJ01", from, to);
````

## API Coverage

| Category | Method | Status | Test | Notes                                                                       |
|----------|--------|--------|------|-----------------------------------------------------------------------------|
| **Album** | `album.getInfo` | ✅ | ✅ |                                                                             |
| | `album.addTags` | ⏳ | ⏳ |                                                                             |
| | `album.getTags` | ⏳ | ⏳ |                                                                             |
| | `album.getTopTags` | ⏳ | ⏳ |                                                                             |
| | `album.removeTag` | ⏳ | ⏳ |                                                                             |
| | `album.search` | ⏳ | ⏳ |                                                                             |
| **Artist** | `artist.getInfo` | ✅ | ✅ |                                                                             |
| | `artist.getSimilar` | ✅ | ✅ |                                                                             |
| | `artist.getCorrection` | ✅ | ✅ |                                                                             |
| | `artist.addTags` | ⏳ | ⏳ |                                                                             |
| | `artist.getTags` | ⏳ | ⏳ |                                                                             |
| | `artist.getTopAlbums` | ⏳ | ⏳ |                                                                             |
| | `artist.getTopTags` | ⏳ | ⏳ |                                                                             |
| | `artist.getTopTracks` | ⏳ | ⏳ |                                                                             |
| | `artist.removeTag` | ⏳ | ⏳ |                                                                             |
| | `artist.search` | ✅ | ✅ |                                                                             |
| **Auth** | `auth.getMobileSession` | ⏳ | ⏳ |                                                                             |
| | `auth.getSession` | ✅ | ⏳ |                                                                             |
| | `auth.getToken` | ✅ | ⏳ |                                                                             |
| **Chart** | `chart.getTopArtists` | ✅ | ⏳ |                                                                             |
| | `chart.getTopTags` | ⏳ | ⏳ |                                                                             |
| | `chart.getTopTracks` | ⏳ | ⏳ |                                                                             |
| **Geo** | `geo.getTopArtists` | ✅ | ⏳ |                                                                             |
| | `geo.getTopTracks` | ⏳ | ⏳ |                                                                             |
| **Library** | `library.getArtists` | ✅ | ⏳ |                                                                             |
| **Tag** | `tag.getInfo` | ✅ | ⏳ |                                                                             |
| | `tag.getSimilar` | ❌ | ❌ | This API call is currently broken and returns an empty array as a response. |
| | `tag.getTopAlbums` | ✅ | ⏳ |                                                                             |
| | `tag.getTopArtists` | ✅ | ⏳ |                                                                             |
| | `tag.getTopTags` | ⏳ | ⏳ |                                                                             |
| | `tag.getTopTracks` | ⏳ | ⏳ |                                                                             |
| | `tag.getWeeklyChartList` | ⏳ | ⏳ |                                                                             |
| **Track** | `track.addTags` | ⏳ | ⏳ |                                                                             |
| | `track.getCorrection` | ⏳ | ⏳ |                                                                             |
| | `track.getInfo` | ✅ | ⏳ |                                                                             |
| | `track.getSimilar` | ⏳ | ⏳ |                                                                             |
| | `track.getTags` | ⏳ | ⏳ |                                                                             |
| | `track.getTopTags` | ⏳ | ⏳ |                                                                             |
| | `track.love` | ✅ | ❌ | This API call doesn't return anything so no test has been made (yet)        |
| | `track.removeTag` | ⏳ | ⏳ |                                                                             |
| | `track.scrobble` | ✅ | ⏳ |                                                                             |
| | `track.search` | ⏳ | ⏳ |                                                                             |
| | `track.unlove` | ✅ | ❌ | This API call doesn't return anything so no test has been made (yet)        |
| | `track.updateNowPlaying` | ⏳ | ⏳ |                                                                             |
| **User** | `user.getFriends` | ⏳ | ⏳ |                                                                             |
| | `user.getInfo` | ✅ | ⏳ |                                                                             |
| | `user.getLovedTracks` | ⏳ | ⏳ |                                                                             |
| | `user.getPersonalTags` | ⏳ | ⏳ |                                                                             |
| | `user.getRecentTracks` | ⏳ | ⏳ |                                                                             |
| | `user.getTopAlbums` | ⏳ | ⏳ |                                                                             |
| | `user.getTopArtists` | ⏳ | ⏳ |                                                                             |
| | `user.getTopTags` | ⏳ | ⏳ |                                                                             |
| | `user.getTopTracks` | ⏳ | ⏳ |                                                                             |
| | `user.getWeeklyAlbumChart` | ⏳ | ⏳ |                                                                             |
| | `user.getWeeklyArtistChart` | ⏳ | ⏳ |                                                                             |
| | `user.getWeeklyChartList` | ⏳ | ⏳ |                                                                             |
| | `user.getWeeklyTrackChart` | ⏳ | ⏳ |                                                                             |

**Legend:** ✅ Implemented | ⏳ Not yet implemented | ❌ Can't/Won't be implemented

## Project Structure

The codebase is organized into layers that depend inward. The domain layer is at the center and doesn't depend on anything external.

### How it works

**`api/`** - This is what users of the library interact with. `LastFmClient` is the entry point that wires everything together.

**`application/`** - Contains services that handle business logic and validation. For example, `ArtistService` validates inputs and calls the gateway to fetch data.

**`domain/`** - The core of the application. `model/` contains your domain entities (like `Artist`), and `port/` defines interfaces that the application layer uses. These interfaces are implemented in the infrastructure layer.

**`infrastructure/`** - This is where external concerns live. `gateway/` contains implementations of the domain interfaces. Each domain (artist, album, etc.) has its own folder with:
- A gateway implementation (e.g., `ArtistGatewayImpl`)
- A mapper that converts Last.fm DTOs to domain models (e.g., `ArtistMapper`)
- A `response/` folder with the Last.fm API response DTOs

DTOs (Data Transfer Objects) are simple classes that match the structure of the Last.fm API responses. They're used to deserialize JSON from the API, and then the mapper converts them into domain models. This keeps the domain layer clean and independent of the API's structure.

The `http/` package provides the HTTP client wrapper used by all gateways.


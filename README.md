# LastFM Java Client

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


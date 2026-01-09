# LastFM Java Client

## Table of Contents

- [Usage](#usage)
- [Project Structure](#project-structure)

## Usage

````java
import io.github.rubeneekhof.lastfm.api.LastFmClient;
import io.github.rubeneekhof.lastfm.application.ArtistGetInfoRequest;

var apiKey = "...";
LastFmClient client = LastFmClient.create(apiKey);

// Simple usage
var artist = client.artists().getInfo("Rina Sawayama");

// With language
var artistEn = client.artists().getInfo("Rina Sawayama", "en");

// With autocorrect
var artistCorrected = client.artists().getInfo("Rina Sawayama", true);

// Full builder pattern with all options
var artistFull = client.artists().getInfo(
    ArtistGetInfoRequest.artist("Rina Sawayama")
        .lang("en")
        .autocorrect(true)
        .username("myusername")
        .build()
);

// Using MBID instead of artist name
var artistByMbid = client.artists().getInfo(
    ArtistGetInfoRequest.mbid("bfcc6d75-a6a5-4bc6-8282-47aec8531818")
        .lang("en")
        .build()
);
````

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


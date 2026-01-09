# LastFM Java Client

## Table of Contents

- [Usage](#usage)
- [Project Structure](#project-structure)

## Usage

````java
var apiKey = "...";
LastFmClient client = LastFmClient.create(apiKey);

var similar = client.artists().getInfo("Rina Sawayama");
System.out.println(similar);
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


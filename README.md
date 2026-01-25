# LastFM Java Client

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.9+-orange)
![Lines of Code](https://raw.githubusercontent.com/RubenJ01/lastfm-java-client/badges/badge.svg)

A Java client for the Last.fm API.

## Quick Start

```java
import io.github.rubeneekhof.lastfm.api.LastFmClient;

var apiKey = "your-api-key-here";
LastFmClient client = LastFmClient.builder()
    .apiKey(apiKey)
    .build();

var artist = client.artists().getInfo("Cher");
```

## Installation

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.RubenJ01</groupId>
        <artifactId>lastfm-java-client</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```

### Gradle

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.RubenJ01:lastfm-java-client:2.0.0'
}
```

## Documentation

📚 **Full documentation is available in the [Wiki](https://github.com/RubenJ01/lastfm-java-client/wiki)**

- **[Installation](https://github.com/RubenJ01/lastfm-java-client/wiki/Installation)** - Maven/Gradle setup
- **[Basic Usage](https://github.com/RubenJ01/lastfm-java-client/wiki/Basic-Usage)** - Getting started guide
- **[Authentication](https://github.com/RubenJ01/lastfm-java-client/wiki/Authentication)** - Setting up authenticated requests
- **[Scrobbling](https://github.com/RubenJ01/lastfm-java-client/wiki/Scrobbling)** - Recording listening history
- **[Pagination](https://github.com/RubenJ01/lastfm-java-client/wiki/Pagination)** - Working with paginated results
- **[UnixTime Utilities](https://github.com/RubenJ01/lastfm-java-client/wiki/UnixTime-Utilities)** - Timestamp helper documentation
- **[API Reference](https://github.com/RubenJ01/lastfm-java-client/wiki/API-Reference)** - Complete API coverage
- **[Project Structure](https://github.com/RubenJ01/lastfm-java-client/wiki/Project-Structure)** - Architecture overview

📋 **[Changelog](CHANGELOG.md)** - Version history and release notes

## What it does

- Builder pattern for requests
- Authentication support
- Scrobbling
- Pagination helpers for iterating over results
- Helper classes for timestamps and dates

## License

MIT License - See [LICENSE](LICENSE) for details.

# LastFM Java Client

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.9+-orange)
![Lines of Code](https://raw.githubusercontent.com/RubenJ01/lastfm-java-client/badges/badge.svg)

A Java client library for the Last.fm API. Built with Clean Architecture principles for maintainability and testability.

## Quick Start

```java
import io.github.rubeneekhof.lastfm.api.LastFmClient;

var apiKey = "your-api-key-here";
LastFmClient client = LastFmClient.create(apiKey);

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
        <version>1.0.0</version>
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
    implementation 'com.github.RubenJ01:lastfm-java-client:1.0.0'
}
```

## Documentation

📚 **Full documentation is available in the [Wiki](https://github.com/RubenJ01/lastfm-java-client/wiki)**

- **[Installation](https://github.com/RubenJ01/lastfm-java-client/wiki/Installation)** - Maven/Gradle setup
- **[Basic Usage](https://github.com/RubenJ01/lastfm-java-client/wiki/Basic-Usage)** - Getting started guide
- **[Authentication](https://github.com/RubenJ01/lastfm-java-client/wiki/Authentication)** - Setting up authenticated requests
- **[Scrobbling](https://github.com/RubenJ01/lastfm-java-client/wiki/Scrobbling)** - Recording listening history
- **[UnixTime Utilities](https://github.com/RubenJ01/lastfm-java-client/wiki/UnixTime-Utilities)** - Timestamp helper documentation
- **[API Reference](https://github.com/RubenJ01/lastfm-java-client/wiki/API-Reference)** - Complete API coverage
- **[Project Structure](https://github.com/RubenJ01/lastfm-java-client/wiki/Project-Structure)** - Architecture overview

## Features

- ✅ Type-safe API with builder pattern
- ✅ Full authentication support
- ✅ Scrobbling capabilities
- ✅ Clean architecture (Hexagonal/Ports & Adapters)
- ✅ Comprehensive error handling
- ✅ Utility classes for common operations

## License

MIT License - See [LICENSE](LICENSE) for details.

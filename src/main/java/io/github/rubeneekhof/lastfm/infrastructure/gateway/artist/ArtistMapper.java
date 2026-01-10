package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;

import java.util.List;
import java.util.Optional;

/**
 * Maps Last.fm API DTOs to domain models.
 * This is the only class allowed to access DTO internals.
 * All collections are guaranteed to be non-null (empty lists instead of null).
 */
public class ArtistMapper {

    /**
     * Maps the full getInfo response to domain model.
     */
    public static Artist from(GetInfoResponse response) {
        if (response == null || response.artist == null) {
            return null;
        }

        GetInfoResponse.ArtistData data = response.artist;
        return new Artist(
                data.name,
                data.mbid,
                data.url,
                mapImages(data.image),
                data.streamable,
                mapStats(data.stats),
                mapSimilar(data.similar),
                mapTags(data.tags),
                mapBio(data.bio),
                null
        );
    }

    /**
     * Maps a similar artist response to domain model.
     */
    public static Artist from(GetSimilarResponse.SimilarArtistData data) {
        if (data == null) {
            return null;
        }

        return new Artist(
                data.name,
                data.mbid,
                data.url,
                mapImagesFromSimilar(data.image),
                data.streamable,
                null,
                List.of(),
                List.of(),
                null,
                data.match
        );
    }

    /**
     * Maps the full getSimilar response to a list of artists.
     */
    public static List<Artist> from(GetSimilarResponse response) {
        if (response == null || response.similarartists == null || response.similarartists.artist == null) {
            return List.of();
        }

        return response.similarartists.artist.stream()
                .map(ArtistMapper::from)
                .toList();
    }

    /**
     * Maps the getCorrection response to an optional corrected artist.
     */
    public static Optional<Artist> from(GetCorrectionResponse response) {
        if (response == null || response.corrections == null || response.corrections.correction == null) {
            return Optional.empty();
        }

        GetCorrectionResponse.Correction correction = response.corrections.correction;
        if (correction.artist == null) {
            return Optional.empty();
        }

        return Optional.of(new Artist(
                correction.artist.name,
                correction.artist.mbid,
                correction.artist.url,
                List.of(),
                null,
                null,
                List.of(),
                List.of(),
                null,
                null
        ));
    }

    /**
     * Maps nested similar artists from getInfo response.
     * Returns minimal artist data (name, mbid, url only).
     */
    private static Artist fromSimilarArtist(GetInfoResponse.ArtistData data) {
        if (data == null) {
            return null;
        }

        return new Artist(
                data.name,
                data.mbid,
                data.url,
                List.of(),
                null,
                null,
                List.of(),
                List.of(),
                null,
                null
        );
    }

    private static List<Artist.Image> mapImages(List<GetInfoResponse.Image> images) {
        return Optional.ofNullable(images)
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(img -> new Artist.Image(img.size, img.url))
                        .toList())
                .orElse(List.of());
    }

    private static List<Artist.Image> mapImagesFromSimilar(List<GetSimilarResponse.Image> images) {
        return Optional.ofNullable(images)
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(img -> new Artist.Image(img.size, img.url))
                        .toList())
                .orElse(List.of());
    }

    private static Artist.Stats mapStats(GetInfoResponse.Stats stats) {
        return Optional.ofNullable(stats)
                .map(s -> new Artist.Stats(s.listeners, s.plays, s.userplaycount))
                .orElse(null);
    }

    private static List<Artist> mapSimilar(GetInfoResponse.Similar similar) {
        return Optional.ofNullable(similar)
                .map(s -> s.artist)
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(ArtistMapper::fromSimilarArtist)
                        .toList())
                .orElse(List.of());
    }

    private static List<Artist.Tag> mapTags(GetInfoResponse.Tags tags) {
        return Optional.ofNullable(tags)
                .map(t -> t.tag)
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(t -> new Artist.Tag(t.name, t.url))
                        .toList())
                .orElse(List.of());
    }

    private static Artist.Bio mapBio(GetInfoResponse.Bio bio) {
        return Optional.ofNullable(bio)
                .map(b -> new Artist.Bio(b.published, b.summary, b.content))
                .orElse(null);
    }
}

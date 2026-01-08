package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;

import java.util.List;

public class ArtistMapper {

    public static Artist toDomain(GetInfoResponse.ArtistData data) {
        if (data == null) return null;

        List<Artist.Image> images = null;
        if (data.image != null) {
            images = data.image.stream()
                    .map(img -> new Artist.Image(img.size, img.url))
                    .toList();
        }

        List<Artist> similar = null;
        if (data.similar != null && data.similar.artist != null) {
            similar = data.similar.artist.stream()
                    .map(a -> new Artist(a.name, a.mbid, a.url, null, null,
                            null, null, null))
                    .toList();
        }

        List<Artist.Tag> tags = null;
        if (data.tags != null && data.tags.tag != null) {
            tags = data.tags.tag.stream()
                    .map(t -> new Artist.Tag(t.name, t.url))
                    .toList();
        }

        Artist.Stats stats = null;
        if (data.stats != null) {
            stats = new Artist.Stats(data.stats.listeners, data.stats.plays);
        }

        Artist.Bio bio = null;
        if (data.bio != null) {
            bio = new Artist.Bio(data.bio.published, data.bio.summary, data.bio.content);
        }

        return new Artist(
                data.name,
                data.mbid,
                data.url,
                images,
                stats,
                similar,
                tags,
                bio
        );
    }

    public static Artist   toDomain(GetSimilarResponse.SimilarArtistData data) {
        if (data == null) return null;

        List<Artist.Image> images = null;
        if (data.image != null) {
            images = data.image.stream()
                    .map(img -> new Artist.Image(img.size, img.url))
                    .toList();
        }

        return new Artist(
                data.name,
                data.mbid,
                data.url,
                images,
                null,
                null,
                null,
                null
        );
    }
}

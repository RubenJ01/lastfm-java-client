package io.github.rubeneekhof.lastfm.application.album;

import io.github.rubeneekhof.lastfm.domain.model.Album;
import io.github.rubeneekhof.lastfm.domain.port.AlbumGateway;

public class AlbumService {

    private final AlbumGateway gateway;

    public AlbumService(AlbumGateway gateway) {
        this.gateway = gateway;
    }

    public Album getInfo(String artist, String album) {
        validateArtistName(artist);
        validateAlbumName(album);
        return getInfo(AlbumGetInfoRequest.artist(artist).album(album).build());
    }

    public Album getInfo(String artist, String album, String lang) {
        validateArtistName(artist);
        validateAlbumName(album);
        return getInfo(AlbumGetInfoRequest.artist(artist).album(album).lang(lang).build());
    }

    public Album getInfo(String artist, String album, boolean autocorrect) {
        validateArtistName(artist);
        validateAlbumName(album);
        return getInfo(AlbumGetInfoRequest.artist(artist).album(album).autocorrect(autocorrect).build());
    }

    public Album getInfo(AlbumGetInfoRequest request) {
        return gateway.getInfo(
                request.artist(),
                request.album(),
                request.mbid(),
                request.autocorrect(),
                request.username(),
                request.lang()
        );
    }

    private void validateArtistName(String artistName) {
        if (artistName == null || artistName.isBlank()) {
            throw new IllegalArgumentException("Artist name must not be blank");
        }
    }

    private void validateAlbumName(String albumName) {
        if (albumName == null || albumName.isBlank()) {
            throw new IllegalArgumentException("Album name must not be blank");
        }
    }
}

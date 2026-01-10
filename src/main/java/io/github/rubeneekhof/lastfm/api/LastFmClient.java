package io.github.rubeneekhof.lastfm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.application.album.AlbumService;
import io.github.rubeneekhof.lastfm.application.artist.ArtistService;
import io.github.rubeneekhof.lastfm.application.chart.ChartService;
import io.github.rubeneekhof.lastfm.application.tag.TagService;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.album.AlbumGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.ArtistGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.chart.ChartGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.TagGatewayImpl;
import io.github.rubeneekhof.lastfm.infrastructure.http.HttpExecutor;

public class LastFmClient {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final ChartService chartService;
    private final TagService tagService;

    private LastFmClient(ArtistService artistService, AlbumService albumService, ChartService chartService, TagService tagService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.chartService = chartService;
        this.tagService = tagService;
    }

    public static LastFmClient create(String apiKey) {
        ObjectMapper mapper = new ObjectMapper();
        HttpExecutor http = new HttpExecutor(apiKey, mapper);
        
        ArtistGatewayImpl artistGateway = new ArtistGatewayImpl(http, mapper);
        ArtistService artistService = new ArtistService(artistGateway);

        AlbumGatewayImpl albumGateway = new AlbumGatewayImpl(http, mapper);
        AlbumService albumService = new AlbumService(albumGateway);

        ChartGatewayImpl chartGateway = new ChartGatewayImpl(http, mapper);
        ChartService chartService = new ChartService(chartGateway);

        TagGatewayImpl tagGateway = new TagGatewayImpl(http, mapper);
        TagService tagService = new TagService(tagGateway);

        return new LastFmClient(artistService, albumService, chartService, tagService);
    }

    public ArtistService artists() {
        return artistService;
    }

    public AlbumService albums() {
        return albumService;
    }

    public ChartService charts() {
        return chartService;
    }

    public TagService tags() {
        return tagService;
    }
}

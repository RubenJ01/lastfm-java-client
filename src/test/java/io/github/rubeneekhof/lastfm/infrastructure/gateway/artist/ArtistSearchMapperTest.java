package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.artist.Artist;
import io.github.rubeneekhof.lastfm.domain.model.artist.ArtistSearchResult;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.SearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtistSearchMapperTest {

    private ObjectMapper objectMapper;
    private static final String SEARCH_JSON_RESPONSE =
            """
                    {
                      "results": {
                        "opensearch:Query": {
                          "#text": "",
                          "role": "request",
                          "searchTerms": "Low",
                          "startPage": "1"
                        },
                        "opensearch:totalResults": "20975",
                        "opensearch:startIndex": "0",
                        "opensearch:itemsPerPage": "5",
                        "artistmatches": {
                          "artist": [
                            {
                              "name": "Low",
                              "listeners": "1049237",
                              "mbid": "42faad37-8aaa-42e4-a300-5a7dae79ed24",
                              "url": "https://www.last.fm/music/Low",
                              "streamable": "0",
                              "image": [
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "small"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "medium"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "large"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "extralarge"
                                }
                              ]
                            },
                            {
                              "name": "Løw",
                              "listeners": "212",
                              "mbid": "",
                              "url": "https://www.last.fm/music/L%C3%B8w",
                              "streamable": "0",
                              "image": [
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "small"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "medium"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "large"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "extralarge"
                                }
                              ]
                            },
                            {
                              "name": "Löw",
                              "listeners": "27",
                              "mbid": "",
                              "url": "https://www.last.fm/music/L%C3%B6w",
                              "streamable": "0",
                              "image": [
                                {
                                  "#text": "",
                                  "size": "small"
                                },
                                {
                                  "#text": "",
                                  "size": "medium"
                                },
                                {
                                  "#text": "",
                                  "size": "large"
                                },
                                {
                                  "#text": "",
                                  "size": "extralarge"
                                }
                              ]
                            },
                            {
                              "name": "All Time Low",
                              "listeners": "2513083",
                              "mbid": "62162215-b023-4f0e-84bd-1e9412d5b32c",
                              "url": "https://www.last.fm/music/All+Time+Low",
                              "streamable": "0",
                              "image": [
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "small"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "medium"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "large"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "extralarge"
                                }
                              ]
                            },
                            {
                              "name": "Low Roar",
                              "listeners": "412430",
                              "mbid": "2921ca1d-206e-411a-8d2e-e12494dcfa9a",
                              "url": "https://www.last.fm/music/Low+Roar",
                              "streamable": "0",
                              "image": [
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "small"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "medium"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "large"
                                },
                                {
                                  "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                  "size": "extralarge"
                                }
                              ]
                            }
                          ]
                        },
                        "@attr": {
                          "for": "Low"
                        }
                      }
                    }
            """;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSearch_MapsAllFieldsCorrectly() throws Exception {
        // Arrange
        SearchResponse response =
                objectMapper.readValue(SEARCH_JSON_RESPONSE, SearchResponse.class);

        // Act
        ArtistSearchResult sut = ArtistMapper.from(response);

        // Assert
        assertNotNull(sut, "Artist search result should be present");
        assertEquals(5, sut.artists().size(), "There should be 5 search results");

        // Basic fields
        assertEquals(5, sut.itemsPerPage(), "Items per page should match");
        assertEquals(0, sut.startIndex(), "Start index should match");
        assertEquals(20975, sut.totalResults(), "Total results should match");

        // Artist
        Artist artist = sut.artists().get(0);

        assertEquals("Low", artist.name(), "Name should match");
        assertEquals(1049237, artist.stats().listeners(), "Listeners should match");
        assertEquals("42faad37-8aaa-42e4-a300-5a7dae79ed24", artist.mbid(),"Mbid should match");
        assertEquals("https://www.last.fm/music/Low", artist.url(), "URL should match");
        assertEquals("0", artist.streamable(), "Streamable should match");


        // Images
        assertNotNull(artist.images(), "Images should not be null");
        assertEquals(4, artist.images().size(), "Should have 4 images");
        assertEquals("small", artist.images().get(0).size(), "First image size should match");
        assertEquals(
                "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                artist.images().get(0).url(),
                "First image URL should match");
        assertEquals("extralarge", artist.images().get(3).size(), "Last image size should be extralarge");
    }
}

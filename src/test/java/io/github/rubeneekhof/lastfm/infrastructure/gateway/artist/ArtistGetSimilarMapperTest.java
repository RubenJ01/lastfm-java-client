package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetSimilarResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtistGetSimilarMapperTest {

    private ObjectMapper objectMapper;
    private static final String GET_SIMILAR_JSON_RESPONSE =
            """
                    {
                      "similarartists": {
                        "artist": [
                          {
                            "name": "LOONA/yyxy",
                            "match": "1",
                            "url": "https://www.last.fm/music/LOONA%2Fyyxy",
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
                              },
                              {
                                "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                "size": "mega"
                              },
                              {
                                "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                "size": ""
                              }
                            ],
                            "streamable": "0"
                          },
                          {
                            "name": "LOOΠΔ 1/3",
                            "match": "0.926323",
                            "url": "https://www.last.fm/music/LOO%CE%A0%CE%94+1%2F3",
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
                              },
                              {
                                "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                "size": "mega"
                              },
                              {
                                "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                "size": ""
                              }
                            ],
                            "streamable": "0"
                          },
                          {
                            "name": "Loossemble",
                            "mbid": "cded24f3-361e-4179-8f26-4209479f6187",
                            "match": "0.876036",
                            "url": "https://www.last.fm/music/Loossemble",
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
                              },
                              {
                                "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                "size": "mega"
                              },
                              {
                                "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                                "size": ""
                              }
                            ],
                            "streamable": "0"
                          }
                        ],
                        "@attr": {
                          "artist": "Loona"
                        }
                      }
                    }
            """;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetSimilar_MapsAllFieldsCorrectly() throws Exception {
        // Arrange
        GetSimilarResponse response =
                objectMapper.readValue(GET_SIMILAR_JSON_RESPONSE, GetSimilarResponse.class);

        // Act
        List<Artist> similarArtists = ArtistMapper.from(response);

        // Assert
        assertFalse(similarArtists.isEmpty(), "Similar artists should be present");
        assertEquals(3, similarArtists.size(), "There should be 3 similar artists");

        // Basic fields
        Artist sut = similarArtists.get(0);

        assertEquals("LOONA/yyxy", sut.name(), "Name should match");
        assertEquals(1, sut.match(), "Match should match");
        assertEquals("https://www.last.fm/music/LOONA%2Fyyxy", sut.url(), "URL should match");
        assertEquals("0", sut.streamable(), "Streamable should match");

        // Images
        assertNotNull(sut.images(), "Images should not be null");
        assertEquals(6, sut.images().size(), "Should have 6 images");
        assertEquals("small", sut.images().get(0).size(), "First image size should match");
        assertEquals(
                "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                sut.images().get(0).url(),
                "First image URL should match");
        assertEquals("", sut.images().get(5).size(), "Last image size should be empty string");

        // Fields that should be empty/null for similar response
        assertNotNull(sut.similar(), "Similar should not be null");
        assertNotNull(sut.tags(), "Tags should not be null");
        assertNull(sut.bio(), "Bio should be null");
        assertNull(sut.stats(), "Stats should be null");
        assertNull(sut.mbid(), "Mbid should be null");
    }
}

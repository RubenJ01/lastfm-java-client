package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistGetCorrectionMapperTest {

  private ObjectMapper objectMapper;
  private static final String GET_CORRECTION_JSON_RESPONSE =
      """
      {
        "corrections": {
          "correction": {
            "artist": {
              "name": "Avicii",
              "mbid": "c85cfd6b-b1e9-4a50-bd55-eb725f04f7d5",
              "url": "https://www.last.fm/music/Avicii"
            },
            "@attr": {
              "index": "0"
            }
          }
        }
      }
      """;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void testGetCorrection_MapsAllFieldsCorrectly() throws Exception {
    // Arrange
    GetCorrectionResponse response =
        objectMapper.readValue(GET_CORRECTION_JSON_RESPONSE, GetCorrectionResponse.class);

    // Act
    var artistOptional = ArtistMapper.from(response);

    // Assert
    assertTrue(artistOptional.isPresent(), "Artist should be present");
    Artist artist = artistOptional.get();

    // Basic fields
    assertEquals("Avicii", artist.name(), "Name should match");
    assertEquals("c85cfd6b-b1e9-4a50-bd55-eb725f04f7d5", artist.mbid(), "MBID should match");
    assertEquals("https://www.last.fm/music/Avicii", artist.url(), "URL should match");

    // Fields that should be empty/null for correction response
    assertNotNull(artist.images(), "Images should not be null");
    assertTrue(artist.images().isEmpty(), "Images should be empty");
    assertNull(artist.streamable(), "Streamable should be null");
    assertNull(artist.stats(), "Stats should be null");
    assertNotNull(artist.similar(), "Similar should not be null");
    assertTrue(artist.similar().isEmpty(), "Similar should be empty");
    assertNotNull(artist.tags(), "Tags should not be null");
    assertTrue(artist.tags().isEmpty(), "Tags should be empty");
    assertNull(artist.bio(), "Bio should be null");
    assertNull(artist.match(), "Match should be null");
  }
}

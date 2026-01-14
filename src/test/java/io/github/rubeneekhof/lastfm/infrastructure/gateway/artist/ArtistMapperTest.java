package io.github.rubeneekhof.lastfm.infrastructure.gateway.artist;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Artist;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetCorrectionResponse;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.artist.response.GetInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistMapperTest {

  private ObjectMapper objectMapper;
  private static final String GET_INFO_JSON_RESPONSE =
      """
      {
        "artist": {
          "name": "Loona",
          "mbid": "cb525a30-b590-448f-b94d-fab86e0e8756",
          "url": "https://www.last.fm/music/Loona",
          "image": [
            {
              "#text": "https://lastfm.freetls.fastly.net/i/u/34s/da8aae0d34f179f4a78866f8601e210d.png",
              "size": "small"
            },
            {
              "#text": "https://lastfm.freetls.fastly.net/i/u/64s/da8aae0d34f179f4a78866f8601e210d.png",
              "size": "medium"
            },
            {
              "#text": "https://lastfm.freetls.fastly.net/i/u/174s/da8aae0d34f179f4a78866f8601e210d.png",
              "size": "large"
            },
            {
              "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/da8aae0d34f179f4a78866f8601e210d.png",
              "size": "extralarge"
            },
            {
              "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/da8aae0d34f179f4a78866f8601e210d.png",
              "size": "mega"
            },
            {
              "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/da8aae0d34f179f4a78866f8601e210d.png",
              "size": ""
            }
          ],
          "streamable": "0",
          "ontour": "0",
          "stats": {
            "listeners": "1117640",
            "playcount": "110133089",
            "userplaycount": "2338"
          },
          "similar": {
            "artist": [
              {
                "name": "LOONA/yyxy",
                "url": "https://www.last.fm/music/LOONA%2Fyyxy",
                "image": [
                  {
                    "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                    "size": "small"
                  }
                ]
              },
              {
                "name": "LOOΠΔ 1/3",
                "url": "https://www.last.fm/music/LOO%CE%A0%CE%94+1%2F3",
                "image": [
                  {
                    "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                    "size": "small"
                  }
                ]
              },
              {
                "name": "Loossemble",
                "url": "https://www.last.fm/music/Loossemble",
                "image": [
                  {
                    "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                    "size": "small"
                  }
                ]
              },
              {
                "name": "LOOΠΔ / ODD EYE CIRCLE",
                "url": "https://www.last.fm/music/LOO%CE%A0%CE%94+%2F+ODD+EYE+CIRCLE",
                "image": [
                  {
                    "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                    "size": "small"
                  }
                ]
              },
              {
                "name": "HeeJin",
                "url": "https://www.last.fm/music/HeeJin",
                "image": [
                  {
                    "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                    "size": "small"
                  }
                ]
              }
            ]
          },
          "tags": {
            "tag": [
              {
                "name": "pop",
                "url": "https://www.last.fm/tag/pop"
              },
              {
                "name": "dance",
                "url": "https://www.last.fm/tag/dance"
              },
              {
                "name": "k-pop",
                "url": "https://www.last.fm/tag/k-pop"
              },
              {
                "name": "latin",
                "url": "https://www.last.fm/tag/latin"
              },
              {
                "name": "female vocalists",
                "url": "https://www.last.fm/tag/female+vocalists"
              }
            ]
          },
          "bio": {
            "links": {
              "link": {
                "#text": "",
                "rel": "original",
                "href": "https://last.fm/music/Loona/+wiki"
              }
            },
            "published": "23 Sep 2006, 22:08",
            "summary": "There are at least 3 artists named Loona:\\n\\n1) LOONA, sometimes stylized as LOOΠΔ (Hangul: 이달의 소녀; lit. Girl of the Month) was a 12 membered South Korean girl group formerly under BlockBerryCreative, a subsidiary of Polaris Entertainment. The group is currently disbanded as a result of all 12 members winning their contract termination lawsuits, and all (aside from Yves and Chuu who are pursuing a solo career) have spread across four different companies at the time of writing this. <a href=\\"https://www.last.fm/music/Loona\\">Read more on Last.fm</a>",
            "content": "There are at least 3 artists named Loona:\\n\\n1) LOONA, sometimes stylized as LOOΠΔ (Hangul: 이달의 소녀; lit. Girl of the Month) was a 12 membered South Korean girl group formerly under BlockBerryCreative, a subsidiary of Polaris Entertainment. The group is currently disbanded as a result of all 12 members winning their contract termination lawsuits, and all (aside from Yves and Chuu who are pursuing a solo career) have spread across four different companies at the time of writing this. The group's members won the right to use the name \\"LOONA\\" for future activities. However, they cannot use the group name freely, which limits their ability to act as a full group."
          }
        }
      }
      """;
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

  @Test
  void testGetInfo_MapsAllFieldsCorrectly() throws Exception {
    // Arrange
    GetInfoResponse response =
        objectMapper.readValue(GET_INFO_JSON_RESPONSE, GetInfoResponse.class);

    // Act
    Artist artist = ArtistMapper.from(response);

    // Assert
    assertNotNull(artist, "Artist should not be null");

    // Basic fields
    assertEquals("Loona", artist.name(), "Name should match");
    assertEquals("cb525a30-b590-448f-b94d-fab86e0e8756", artist.mbid(), "MBID should match");
    assertEquals("https://www.last.fm/music/Loona", artist.url(), "URL should match");
    assertEquals("0", artist.streamable(), "Streamable should match");

    // Images
    assertNotNull(artist.images(), "Images should not be null");
    assertEquals(6, artist.images().size(), "Should have 6 images");
    assertEquals("small", artist.images().get(0).size(), "First image size should match");
    assertEquals(
        "https://lastfm.freetls.fastly.net/i/u/34s/da8aae0d34f179f4a78866f8601e210d.png",
        artist.images().get(0).url(),
        "First image URL should match");
    assertEquals("", artist.images().get(5).size(), "Last image size should be empty string");

    // Stats
    assertNotNull(artist.stats(), "Stats should not be null");
    assertEquals(1117640, artist.stats().listeners(), "Listeners should match");
    assertEquals(110133089, artist.stats().plays(), "Plays should match");
    assertEquals(2338, artist.stats().userplaycount(), "User play count should match");

    // Similar artists
    assertNotNull(artist.similar(), "Similar artists should not be null");
    assertEquals(5, artist.similar().size(), "Should have 5 similar artists");
    assertEquals(
        "LOONA/yyxy", artist.similar().get(0).name(), "First similar artist name should match");
    assertEquals(
        "https://www.last.fm/music/LOONA%2Fyyxy",
        artist.similar().get(0).url(), "First similar artist URL should match");
    assertEquals("HeeJin", artist.similar().get(4).name(), "Last similar artist name should match");

    // Tags
    assertNotNull(artist.tags(), "Tags should not be null");
    assertEquals(5, artist.tags().size(), "Should have 5 tags");
    assertEquals("pop", artist.tags().get(0).name(), "First tag name should match");
    assertEquals(
        "https://www.last.fm/tag/pop", artist.tags().get(0).url(), "First tag URL should match");
    assertEquals("female vocalists", artist.tags().get(4).name(), "Last tag name should match");

    // Bio
    assertNotNull(artist.bio(), "Bio should not be null");
    assertEquals("23 Sep 2006, 22:08", artist.bio().published(), "Bio published should match");
    assertTrue(
        artist.bio().summary().contains("There are at least 3 artists named Loona"),
        "Bio summary should contain expected text");
    assertTrue(
        artist.bio().content().contains("LOONA, sometimes stylized as LOOΠΔ"),
        "Bio content should contain expected text");
  }
}

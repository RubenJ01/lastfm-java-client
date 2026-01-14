package io.github.rubeneekhof.lastfm.infrastructure.gateway.album;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.Album;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.album.response.GetInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlbumMapperTest {

  private ObjectMapper objectMapper;
  private static final String GET_INFO_JSON_RESPONSE =
      """
              {
                 "album": {
                   "artist": "Caroline Polachek",
                   "mbid": "3913f9b2-8622-4b0b-aeb5-3511d2b103bc",
                   "tags": {
                     "tag": [
                       {
                         "url": "https://www.last.fm/tag/art+pop",
                         "name": "art pop"
                       },
                       {
                         "url": "https://www.last.fm/tag/alternative+rnb",
                         "name": "alternative rnb"
                       },
                       {
                         "url": "https://www.last.fm/tag/ambient+pop",
                         "name": "ambient pop"
                       },
                       {
                         "url": "https://www.last.fm/tag/synthpop",
                         "name": "synthpop"
                       },
                       {
                         "url": "https://www.last.fm/tag/pop",
                         "name": "pop"
                       }
                     ]
                   },
                   "name": "Pang",
                   "userplaycount": 41,
                   "image": [
                     {
                       "size": "small",
                       "#text": "https://lastfm.freetls.fastly.net/i/u/34s/2da4833340c7563d1f06ed19c0661748.png"
                     },
                     {
                       "size": "medium",
                       "#text": "https://lastfm.freetls.fastly.net/i/u/64s/2da4833340c7563d1f06ed19c0661748.png"
                     },
                     {
                       "size": "large",
                       "#text": "https://lastfm.freetls.fastly.net/i/u/174s/2da4833340c7563d1f06ed19c0661748.png"
                     },
                     {
                       "size": "extralarge",
                       "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2da4833340c7563d1f06ed19c0661748.png"
                     },
                     {
                       "size": "mega",
                       "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2da4833340c7563d1f06ed19c0661748.png"
                     },
                     {
                       "size": "",
                       "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/2da4833340c7563d1f06ed19c0661748.png"
                     }
                   ],
                   "tracks": {
                     "track": [
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 102,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/The+Gate",
                         "name": "The Gate",
                         "@attr": {
                           "rank": 1
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 213,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Pang",
                         "name": "Pang",
                         "@attr": {
                           "rank": 2
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 154,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/New+Normal",
                         "name": "New Normal",
                         "@attr": {
                           "rank": 3
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": null,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Hit+Me+Where+It+Hurts",
                         "name": "Hit Me Where It Hurts",
                         "@attr": {
                           "rank": 4
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 187,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/I+Give+Up",
                         "name": "I Give Up",
                         "@attr": {
                           "rank": 5
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 184,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Look+at+Me+Now",
                         "name": "Look at Me Now",
                         "@attr": {
                           "rank": 6
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 194,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Insomnia",
                         "name": "Insomnia",
                         "@attr": {
                           "rank": 7
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 204,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Ocean+of+Tears",
                         "name": "Ocean of Tears",
                         "@attr": {
                           "rank": 8
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 234,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Hey+Big+Eyes",
                         "name": "Hey Big Eyes",
                         "@attr": {
                           "rank": 9
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 207,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Go+as+a+Dream",
                         "name": "Go as a Dream",
                         "@attr": {
                           "rank": 10
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 212,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Caroline+Shut+Up",
                         "name": "Caroline Shut Up",
                         "@attr": {
                           "rank": 11
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 183,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/So+Hot+You%27re+Hurting+My+Feelings",
                         "name": "So Hot You're Hurting My Feelings",
                         "@attr": {
                           "rank": 12
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 322,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Door",
                         "name": "Door",
                         "@attr": {
                           "rank": 13
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       },
                       {
                         "streamable": {
                           "fulltrack": "0",
                           "#text": "0"
                         },
                         "duration": 212,
                         "url": "https://www.last.fm/music/Caroline+Polachek/Pang/Parachute",
                         "name": "Parachute",
                         "@attr": {
                           "rank": 14
                         },
                         "artist": {
                           "url": "https://www.last.fm/music/Caroline+Polachek",
                           "name": "Caroline Polachek",
                           "mbid": "d8f43dc5-4613-48ac-8c23-a62b82f8c67a"
                         }
                       }
                     ]
                   },
                   "listeners": "740267",
                   "playcount": "34469014",
                   "url": "https://www.last.fm/music/Caroline+Polachek/Pang",
                   "wiki": {
                     "published": "12 Dec 2024, 19:57",
                     "summary": "Pang is the third studio album by American singer Caroline Polachek, and first under her given name, released on October 18, 2019, through Sony Music, The Orchard and Polachek's imprint Perpetual Novice. The album received critical acclaim from and was placed on several critics' year-end lists, topping Dazed's list. Commercially, Pangpeaked at number 17 on the Billboard Heatseekers Albums chart and number 40 on the Independent Albums chart, making it Polachek's first charting solo album. <a href=\\"https://www.last.fm/music/Caroline+Polachek/Pang\\">Read more on Last.fm</a>.",
                     "content": "Pang is the third studio album by American singer Caroline Polachek, and first under her given name, released on October 18, 2019, through Sony Music, The Orchard and Polachek's imprint Perpetual Novice.\\n\\nThe album received critical acclaim from and was placed on several critics' year-end lists, topping Dazed's list. Commercially, Pangpeaked at number 17 on the Billboard Heatseekers Albums chart and number 40 on the Independent Albums chart, making it Polachek's first charting solo album. <a href=\\"https://www.last.fm/music/Caroline+Polachek/Pang\\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply."
                   }
                 }
               }
      """;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void testGetInfo_MapsAllFieldsCorrectly() throws Exception {
    // Arrange
    GetInfoResponse response = objectMapper.readValue(GET_INFO_JSON_RESPONSE, GetInfoResponse.class);

    // Act
    Album album = AlbumMapper.from(response);

    // Assert
    assertNotNull(album, "Album should not be null");

    // Basic fields
    assertEquals("Pang", album.name(), "Album name should match");
    assertEquals("Caroline Polachek", album.artist(), "Artist name should match");
    assertEquals("3913f9b2-8622-4b0b-aeb5-3511d2b103bc", album.mbid(), "MBID should match");
    assertEquals(
        "https://www.last.fm/music/Caroline+Polachek/Pang", album.url(), "URL should match");

    // Release date
    assertEquals(
        "12 Dec 2024, 19:57", album.releaseDate(), "Release date should use wiki.published");

    // Images
    assertNotNull(album.images(), "Images should not be null");
    assertEquals(6, album.images().size(), "Should have 6 images");
    assertEquals("small", album.images().get(0).size(), "First image size should be 'small'");
    assertEquals(
        "https://lastfm.freetls.fastly.net/i/u/34s/2da4833340c7563d1f06ed19c0661748.png",
        album.images().get(0).url(),
        "First image URL should match");
    assertEquals("medium", album.images().get(1).size(), "Second image size should be 'medium'");
    assertEquals("", album.images().get(5).size(), "Last image size should be empty string");

    // Stats
    assertNotNull(album.stats(), "Stats should not be null");
    assertEquals(740267, album.stats().listeners(), "Listeners count should match");
    assertEquals(34469014, album.stats().plays(), "Play count should match");
    assertEquals(41, album.stats().userplaycount(), "User play count should match");

    // Tags
    assertNotNull(album.tags(), "Tags should not be null");
    assertEquals(5, album.tags().size(), "Should have 5 tags");
    assertEquals("art pop", album.tags().get(0).name(), "First tag name should match");
    assertEquals(
        "https://www.last.fm/tag/art+pop", album.tags().get(0).url(), "First tag URL should match");
    assertEquals("pop", album.tags().get(4).name(), "Last tag name should match");

    // Tracks
    assertNotNull(album.tracks(), "Tracks should not be null");
    assertEquals(14, album.tracks().size(), "Should have 14 tracks");

    // First track
    Album.Track firstTrack = album.tracks().get(0);
    assertEquals("The Gate", firstTrack.name(), "First track name should match");
    assertEquals(102, firstTrack.duration(), "First track duration should match");
    assertEquals(
        "https://www.last.fm/music/Caroline+Polachek/Pang/The+Gate",
        firstTrack.url(),
        "First track URL should match");
    assertEquals("0", firstTrack.streamable(), "First track streamable should match");
    assertEquals(
        "Caroline Polachek", firstTrack.artistName(), "First track artist name should match");
    assertEquals(
        "d8f43dc5-4613-48ac-8c23-a62b82f8c67a",
        firstTrack.artistMbid(),
        "First track artist MBID should match");
    assertEquals(1, firstTrack.rank(), "First track rank should match");

    // Track with null duration
    Album.Track trackWithNullDuration = album.tracks().get(3);
    assertEquals("Hit Me Where It Hurts", trackWithNullDuration.name(), "Track name should match");
    assertNull(trackWithNullDuration.duration(), "Track duration should be null");
    assertEquals(4, trackWithNullDuration.rank(), "Track rank should match");

    // Wiki
    assertNotNull(album.wiki(), "Wiki should not be null");
    assertEquals(
        "12 Dec 2024, 19:57", album.wiki().published(), "Wiki published date should match");
    assertNotNull(album.wiki().summary(), "Wiki summary should not be null");
    assertTrue(
        album.wiki().summary().contains("Pang is the third studio album"),
        "Wiki summary should contain expected text");
    assertNotNull(album.wiki().content(), "Wiki content should not be null");
    assertTrue(
        album.wiki().content().contains("Pang is the third studio album"),
        "Wiki content should contain expected text");
  }
}

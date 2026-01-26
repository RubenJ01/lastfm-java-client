package io.github.rubeneekhof.lastfm.infrastructure.gateway.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubeneekhof.lastfm.domain.model.user.FriendsResult;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.user.response.GetFriendsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserGetFriendsMapperTest {

    private ObjectMapper objectMapper;
    private static final String GET_FRIENDS_JSON_RESPONSE =
            """
                            {
                              "friends": {
                                "@attr": {
                                  "user": "RubenJ01",
                                  "totalPages": "1",
                                  "page": "1",
                                  "perPage": "50",
                                  "total": "9"
                                },
                                "user": [
                                  {
                                    "name": "JiafeiCakKe31",
                                    "url": "https://www.last.fm/user/JiafeiCakKe31",
                                    "country": "Turkey",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/f5555ac0a288e7abd9dbd33592fcee0e.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/f5555ac0a288e7abd9dbd33592fcee0e.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/f5555ac0a288e7abd9dbd33592fcee0e.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/f5555ac0a288e7abd9dbd33592fcee0e.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1669823043",
                                      "#text": "2022-11-30 15:44"
                                    },
                                    "realname": "Arül",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "Kwogfaf",
                                    "url": "https://www.last.fm/user/Kwogfaf",
                                    "country": "United States",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/9904fdc3e2c6eaa46d1647b89f639fa2.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/9904fdc3e2c6eaa46d1647b89f639fa2.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/9904fdc3e2c6eaa46d1647b89f639fa2.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/9904fdc3e2c6eaa46d1647b89f639fa2.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1361229056",
                                      "#text": "2013-02-18 23:10"
                                    },
                                    "realname": "Stephen",
                                    "subscriber": "1",
                                    "bootstrap": "0",
                                    "type": "subscriber"
                                  },
                                  {
                                    "name": "lachguso",
                                    "url": "https://www.last.fm/user/lachguso",
                                    "country": "None",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": ""
                                      },
                                      {
                                        "size": "medium",
                                        "#text": ""
                                      },
                                      {
                                        "size": "large",
                                        "#text": ""
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": ""
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1598904250",
                                      "#text": "2020-08-31 20:04"
                                    },
                                    "realname": "",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "SubwayRuben",
                                    "url": "https://www.last.fm/user/SubwayRuben",
                                    "country": "None",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": ""
                                      },
                                      {
                                        "size": "medium",
                                        "#text": ""
                                      },
                                      {
                                        "size": "large",
                                        "#text": ""
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": ""
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1748994437",
                                      "#text": "2025-06-03 23:47"
                                    },
                                    "realname": "",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "TheGreep",
                                    "url": "https://www.last.fm/user/TheGreep",
                                    "country": "United Kingdom",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/c4412fb0e6004d79cd6d0001d1904e7a.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/c4412fb0e6004d79cd6d0001d1904e7a.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/c4412fb0e6004d79cd6d0001d1904e7a.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/c4412fb0e6004d79cd6d0001d1904e7a.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1372885715",
                                      "#text": "2013-07-03 21:08"
                                    },
                                    "realname": "The Greep",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "Hera030",
                                    "url": "https://www.last.fm/user/Hera030",
                                    "country": "None",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/e2477ca9e946c1919c9f8da479eac3fd.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/e2477ca9e946c1919c9f8da479eac3fd.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/e2477ca9e946c1919c9f8da479eac3fd.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/e2477ca9e946c1919c9f8da479eac3fd.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1729684341",
                                      "#text": "2024-10-23 11:52"
                                    },
                                    "realname": "Hera",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "Flipsing",
                                    "url": "https://www.last.fm/user/Flipsing",
                                    "country": "None",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/03beaba8e7119a7756e91709e42e8b01.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/03beaba8e7119a7756e91709e42e8b01.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/03beaba8e7119a7756e91709e42e8b01.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/03beaba8e7119a7756e91709e42e8b01.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1722286882",
                                      "#text": "2024-07-29 21:01"
                                    },
                                    "realname": "",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "Mannenwarmer",
                                    "url": "https://www.last.fm/user/Mannenwarmer",
                                    "country": "Netherlands",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/cbddea2c8b9a1b205d5977f59b86280c.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/cbddea2c8b9a1b205d5977f59b86280c.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/cbddea2c8b9a1b205d5977f59b86280c.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/cbddea2c8b9a1b205d5977f59b86280c.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1722169059",
                                      "#text": "2024-07-28 12:17"
                                    },
                                    "realname": "Mannenwarmer",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  },
                                  {
                                    "name": "Alexander__G",
                                    "url": "https://www.last.fm/user/Alexander__G",
                                    "country": "Netherlands",
                                    "playlists": "0",
                                    "playcount": "0",
                                    "image": [
                                      {
                                        "size": "small",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/34s/047baa9c05b01ced01df59bdfa4977c6.png"
                                      },
                                      {
                                        "size": "medium",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/64s/047baa9c05b01ced01df59bdfa4977c6.png"
                                      },
                                      {
                                        "size": "large",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/174s/047baa9c05b01ced01df59bdfa4977c6.png"
                                      },
                                      {
                                        "size": "extralarge",
                                        "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/047baa9c05b01ced01df59bdfa4977c6.png"
                                      }
                                    ],
                                    "registered": {
                                      "unixtime": "1671057239",
                                      "#text": "2022-12-14 22:33"
                                    },
                                    "realname": "Alexander",
                                    "subscriber": "0",
                                    "bootstrap": "0",
                                    "type": "user"
                                  }
                                ]
                              }
                            }
                    """;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetFriends_MapsAllFieldsCorrectly() throws Exception {
        // Arrange
        GetFriendsResponse response =
                objectMapper.readValue(GET_FRIENDS_JSON_RESPONSE, GetFriendsResponse.class);

        // Act
        FriendsResult result = UserMapper.from(response);

        // Assert
        assertNotNull(result, "FriendsResult should not be null");

        // Pagination attributes
        assertEquals("RubenJ01", result.user(), "User should match");
        assertEquals(1, result.page(), "Page should match");
        assertEquals(9, result.total(), "Total should match");
        assertEquals(50, result.perPage(), "PerPage should match");
        assertEquals(1, result.totalPages(), "TotalPages should match");

        // Friends list
        assertNotNull(result.friends(), "Friends list should not be null");
        assertEquals(9, result.friends().size(), "Should have 9 friends");

        // First friend
        FriendsResult.Friend firstFriend = result.friends().get(0);
        assertEquals("JiafeiCakKe31", firstFriend.name(), "First friend name should match");
        assertEquals("Arül", firstFriend.realname(), "First friend realname should match");
        assertEquals(
                "https://www.last.fm/user/JiafeiCakKe31",
                firstFriend.url(),
                "First friend URL should match");
        assertEquals("Turkey", firstFriend.country(), "First friend country should match");
        assertEquals(0, firstFriend.playlists(), "First friend playlists should match");
        assertEquals(0, firstFriend.playcount(), "First friend playcount should match");
        assertFalse(firstFriend.subscriber(), "First friend should not be subscriber");
        assertEquals("user", firstFriend.type(), "First friend type should match");
        assertEquals(1669823043L, firstFriend.registered(), "First friend registered should match");
        assertEquals(0, firstFriend.bootstrap(), "First friend bootstrap should match");

        // First friend images
        assertNotNull(firstFriend.images(), "First friend images should not be null");
        assertEquals(4, firstFriend.images().size(), "First friend should have 4 images");
        assertEquals("small", firstFriend.images().get(0).size(), "First image size should match");
        assertEquals(
                "https://lastfm.freetls.fastly.net/i/u/34s/f5555ac0a288e7abd9dbd33592fcee0e.png",
                firstFriend.images().get(0).url(),
                "First image URL should match");
        assertEquals("extralarge", firstFriend.images().get(3).size(), "Last image size should match");

        // Second friend
        FriendsResult.Friend secondFriend = result.friends().get(1);
        assertEquals("Kwogfaf", secondFriend.name(), "Second friend name should match");
        assertEquals("Stephen", secondFriend.realname(), "Second friend realname should match");
        assertEquals("United States", secondFriend.country(), "Second friend country should match");
        assertTrue(secondFriend.subscriber(), "Second friend should be subscriber");
        assertEquals("subscriber", secondFriend.type(), "Second friend type should match");
        assertEquals(1361229056L, secondFriend.registered(), "Second friend registered should match");

        // Third friend
        FriendsResult.Friend thirdFriend = result.friends().get(2);
        assertEquals("lachguso", thirdFriend.name(), "Third friend name should match");
        assertEquals("", thirdFriend.realname(), "Third friend realname should be empty");
        assertEquals("None", thirdFriend.country(), "Third friend country should match");
        assertNotNull(thirdFriend.images(), "Third friend images should not be null");
        assertEquals(4, thirdFriend.images().size(), "Third friend should have 4 images");
        assertEquals("", thirdFriend.images().get(0).url(), "Third friend first image URL should be empty");

        // Last friend
        FriendsResult.Friend lastFriend = result.friends().get(8);
        assertEquals("Alexander__G", lastFriend.name(), "Last friend name should match");
        assertEquals("Alexander", lastFriend.realname(), "Last friend realname should match");
        assertEquals("Netherlands", lastFriend.country(), "Last friend country should match");
        assertEquals(1671057239L, lastFriend.registered(), "Last friend registered should match");
    }
}

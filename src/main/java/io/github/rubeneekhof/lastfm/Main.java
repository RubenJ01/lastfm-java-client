package io.github.rubeneekhof.lastfm;

import io.github.rubeneekhof.lastfm.api.LastFmClient;

public class Main {
    public static void main(String[] args) {
        String apiKey = "d2641f650f1b592e6f66aeb68755e43b";
        LastFmClient client = LastFmClient.create(apiKey);

        var similar = client.artists().getInfo("OPIFHIEGU");
        System.out.println(similar);
    }
}

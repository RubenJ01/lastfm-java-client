# LastFM Java Client

## Usage

````java
var apiKey = "...";
LastFmClient client = LastFmClient.create(apiKey);

var similar = client.artists().getInfo("Rina Sawayama");
System.out.println(similar);
````
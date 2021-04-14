package сlient;

import com.google.gson.*;
import model.Album;
import parser.JsonParserSpotify;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class SpotifyHttpClient {

    private final HttpClient client;
    private final JsonParserSpotify parser;

    public SpotifyHttpClient() {
        client = HttpClient.newBuilder().build();
        parser = new JsonParserSpotify();
    }

    public List<Album> getNewReleases() throws IOException, InterruptedException {
        String token = getToken();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("https://api.spotify.com/v1/browse/new-releases?country=RU&limit=7"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return parser.parseJsonNewReleases(response);
    }

    public Map<String, String> getPlaylists() throws IOException, InterruptedException {
        String token = getToken();
        String[] categories = {"pop", "hiphop", "edm_dance", "rnb", "rock",
                "toplists", "mood", "alternative", "at_home", "indie_alt"};
        Random rand = new Random();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("https://api.spotify.com/v1/browse/categories/"+ categories[rand.nextInt(10)] +
                        "/playlists?&limit=30"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return parser.parseJsonPlaylists(response);
    }

    public String getToken() throws IOException, InterruptedException {
        String url_token = "https://accounts.spotify.com/api/token";
        HttpRequest requestToken = HttpRequest.newBuilder()
                .headers("Authorization", "Basic NjMwNWRiMDk4NTkyNGZmODhhZDc3MGZlOTU1MmVmNjE6YTgxMGZhNWYwOTljNDkzZjg4YWJiYTYxYzVlMzYyOGE=",
                        "Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(url_token))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = client.send(requestToken, HttpResponse.BodyHandlers.ofString());

        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        String token = jo.get("access_token").getAsString();

        return token;
    }
}

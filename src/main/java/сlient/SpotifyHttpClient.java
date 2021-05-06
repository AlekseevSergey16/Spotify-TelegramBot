package сlient;

import com.google.gson.*;
import model.Album;
import parser.ParserSpotify;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SpotifyHttpClient {

    private final HttpClient client;

    public SpotifyHttpClient() {
        client = HttpClient.newBuilder().build();
    }

    public List<Album> getNewReleases() throws IOException, InterruptedException {
        String token = getToken();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("https://api.spotify.com/v1/browse/new-releases?country=RU&limit=7"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return ParserSpotify.parseJsonNewReleases(response);
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

        return ParserSpotify.parseJsonPlaylists(response);
    }

    public List<Album> getAlbums(String album, String artist) throws IOException, InterruptedException {
        String token = getToken();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("https://api.spotify.com/v1/search?q=album:"+ URLEncoder.encode(album, StandardCharsets.UTF_8)
                        +"%20artist:"+URLEncoder.encode(artist, StandardCharsets.UTF_8)+"&type=album&market=RU"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return ParserSpotify.parseJsonNewReleases(response);
    }

    public List<Album> getArtist(String artist) throws IOException, InterruptedException {
        String token = getToken();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("https://api.spotify.com/v1/artists/"+getArtistId(artist)+"/albums?include_groups=album&market=RU"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return ParserSpotify.parseJsonAlbums(response);
    }

    public String getArtistId(String artist) throws IOException, InterruptedException {
        String token = getToken();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("https://api.spotify.com/v1/search?q=" + URLEncoder.encode(artist, StandardCharsets.UTF_8)
                        + "&type=artist&market=RU&limit=1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return ParserSpotify.parseJsonArtist(response);
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

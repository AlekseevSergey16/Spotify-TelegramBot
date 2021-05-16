package сlient;

import model.Track;
import parser.ParserGenius;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GeniusHttpClient {

    private final String TOKEN = "71m0rHPhDoxe5OuXauIXSnT9cc9jawDmgkVw2KngXz3BHmi3Szevm-q-6rWUoB95";

    private final HttpClient client;

    public GeniusHttpClient() {
        client = HttpClient.newBuilder().build();
    }

    public Track getTrack(String artist, String track) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + TOKEN)
                .uri(URI.create("https://api.genius.com/search?q="
                        + URLEncoder.encode(artist, StandardCharsets.UTF_8)
                        + "%20"
                        + URLEncoder.encode(track, StandardCharsets.UTF_8)))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String songId = ParserGenius.getSongId(httpResponse);

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + TOKEN)
                .uri(URI.create("https://api.genius.com/songs/" + songId + "?text_format=plain"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return ParserGenius.getTrack(response);
    }


}

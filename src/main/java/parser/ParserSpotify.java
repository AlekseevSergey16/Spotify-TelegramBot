package parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Album;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserSpotify {
    public static List<Album> parseJsonNewReleases(HttpResponse<String> response) {
        List<Album> albums = new ArrayList<>();
        JsonObject responseJson  = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject jsonObject = responseJson.get("albums").getAsJsonObject();
        for (JsonElement item : jsonObject.getAsJsonArray("items")) {
            String albumName = item.getAsJsonObject().get("name").getAsString();
            StringBuilder nameArtist = new StringBuilder();
            JsonArray artists = item.getAsJsonObject().getAsJsonArray("artists");
            int size = artists.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        nameArtist.append(artists.get(i).getAsJsonObject().get("name").getAsString());
                        break;
                    }
                    nameArtist.append(artists.get(i).getAsJsonObject().get("name").getAsString()).append(" ◦ ");
                }
            }
            String urlSpotify = item.getAsJsonObject()
                    .get("external_urls")
                    .getAsJsonObject()
                    .get("spotify")
                    .getAsString();

            String releaseDate = item.getAsJsonObject().get("release_date").getAsString();
            int totalTracks = Integer.parseInt(item.getAsJsonObject().get("total_tracks").getAsString());
            String artist = nameArtist.toString();

            albums.add(new Album(albumName, artist, releaseDate, totalTracks, urlSpotify));
        }
        return albums;
    }

    public static String parseJsonArtist(HttpResponse<String> response) {
        JsonObject responseJson  = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject jsonObject = responseJson.get("artists").getAsJsonObject();
        JsonElement item = jsonObject.getAsJsonArray("items").get(0);
        String artistId = item.getAsJsonObject().get("id").getAsString();
        return artistId;
    }

    public static Map<String, String> parseJsonPlaylists(HttpResponse<String> response) {
        Map<String, String> playlists = new HashMap<>();

        JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject jsonObject = responseJson.get("playlists").getAsJsonObject();

        for (JsonElement item : jsonObject.getAsJsonArray("items")) {
            String playlistName = item.getAsJsonObject().get("name").getAsString();
            String urlSpotify = item.getAsJsonObject()
                    .get("external_urls")
                    .getAsJsonObject()
                    .get("spotify")
                    .getAsString();
            playlists.put(playlistName, urlSpotify);
        }

        return playlists;
    }

    public static List<Album> parseJsonAlbums(HttpResponse<String> response) {
        List<Album> albums = new ArrayList<>();

        JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
        //JsonObject jsonObject = responseJson.get("albums").getAsJsonObject();
        for (JsonElement item : responseJson.getAsJsonArray("items")) {
            String title = item.getAsJsonObject().get("name").getAsString();
            StringBuilder nameArtist = new StringBuilder();
            JsonArray artists = item.getAsJsonObject().getAsJsonArray("artists");
            int size = artists.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        nameArtist.append(artists.get(i).getAsJsonObject().get("name").getAsString());
                        break;
                    }
                    nameArtist.append(artists.get(i).getAsJsonObject().get("name").getAsString()).append(" ◦ ");
                }
            }
            String urlSpotify = item.getAsJsonObject()
                    .get("external_urls")
                    .getAsJsonObject()
                    .get("spotify")
                    .getAsString();

            String releaseDate = item.getAsJsonObject().get("release_date").getAsString();
            int totalTracks = Integer.parseInt(item.getAsJsonObject().get("total_tracks").getAsString());
            String artist = nameArtist.toString();

            albums.add(new Album(title, artist, releaseDate, totalTracks, urlSpotify));
        }

        return albums;
    }
}

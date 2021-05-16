package parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Track;

import java.net.http.HttpResponse;

public class ParserGenius {

    private ParserGenius() {}

    public static String getSongId(HttpResponse<String> response) {
        JsonObject responseJson  = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject jsonObject = responseJson.get("response").getAsJsonObject();
        JsonElement item = jsonObject.getAsJsonArray("hits").get(0);
        String songId = item.getAsJsonObject().get("result").getAsJsonObject().get("id").getAsString();
        return songId;
    }

    public static Track getTrack(HttpResponse<String> response) {
        JsonObject responseJson  = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject jsonObject = responseJson.get("response").getAsJsonObject();
        JsonObject song = jsonObject.get("song").getAsJsonObject();

        String title = song.getAsJsonObject().get("full_title").getAsString();
        String description = song.get("description").getAsJsonObject().get("plain").getAsString();
        String releaseDate = song.get("release_date").getAsString();
        String url = song.get("url").getAsString();

        return new Track(title, description, releaseDate, url);
    }

}

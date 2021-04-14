package command;

import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import сlient.SpotifyHttpClient;

import java.io.IOException;
import java.util.Map;

public class PlaylistsCommand implements Command {

    private SpotifyHttpClient spotifyClient;
    private SendBotMessageService messageService;
    private Map<String, String> playlists;

    public PlaylistsCommand(SendBotMessageService messageService, SpotifyHttpClient spotifyClient) {
        this.messageService = messageService;
        this.spotifyClient = spotifyClient;
    }

    @Override
    public void execute(Update update) {
        try {
            playlists = spotifyClient.getPlaylists();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String> playlist : playlists.entrySet()) {
            String message = "Плейлист: " + playlist.getKey() + "\n" + playlist.getValue();
            messageService.sendMessage(update.getMessage().getChatId().toString(), message);
        }
    }
}

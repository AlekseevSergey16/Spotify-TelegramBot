package command;

import model.Album;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import сlient.SpotifyHttpClient;
import java.io.IOException;
import java.util.List;

public class NewReleasesCommand implements Command {

    private SpotifyHttpClient spotifyClient;
    private SendBotMessageService messageService;
    private List<Album> albums;

    public NewReleasesCommand(SendBotMessageService messageService, SpotifyHttpClient spotifyClient) {
        this.messageService = messageService;
        this.spotifyClient = spotifyClient;
    }

    @Override
    public void execute(Update update) {
        update.getMessage().getAuthorSignature();
        try {
             albums = spotifyClient.getNewReleases();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        for (Album album : albums) {
            messageService.sendMessage(update.getMessage().getChatId().toString(), album.toString());
        }
    }
}

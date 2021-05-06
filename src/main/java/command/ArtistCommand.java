package command;

import model.Album;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import service.SendBotMessageServiceImpl;
import сlient.SpotifyHttpClient;

import java.io.IOException;
import java.util.List;

public class ArtistCommand implements Command {

    private final SpotifyHttpClient spotifyClient;
    private final SendBotMessageService messageService;
    private List<Album> albums;

    public ArtistCommand(SendBotMessageService messageService, SpotifyHttpClient spotifyClient) {
        this.messageService = messageService;
        this.spotifyClient = spotifyClient;
    }

    @Override
    public void execute(Update update) {
        String text = update.getMessage().getText();
        String[] message = text.split(" ");
        String artistName = message[1];
        String artist = artistName.replace("_", " ");
        try {
            albums = spotifyClient.getArtist(artist);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        SendBotMessageServiceImpl sendBotMessageService = (SendBotMessageServiceImpl)messageService;

        for (Album album : albums) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), album.toString(), album);
        }
    }
}

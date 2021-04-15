package command;

import model.Album;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import сlient.SpotifyHttpClient;

import java.io.IOException;
import java.util.List;

public class SearchAlbumCommand implements Command {

    private SpotifyHttpClient spotifyClient;
    private SendBotMessageService messageService;
    private List<Album> albums;

    public SearchAlbumCommand(SendBotMessageService messageService, SpotifyHttpClient spotifyClient) {
        this.messageService = messageService;
        this.spotifyClient = spotifyClient;
    }

    @Override
    public void execute(Update update) {
        String text = update.getMessage().getText();
        String[] message = text.split(" ");
        String[] albumAndArtist = message[1].split("-");

        String artistName = albumAndArtist[0];
        String art = artistName.replace(" ", "%20");
        String artist = art.replace("_", "%20");

        String nameAlbum = albumAndArtist[1];
        String alb = nameAlbum.replace(" ", "%20");
        String albumTitle = alb.replace("_", "%20");

        try {
            albums = spotifyClient.getAlbums(albumTitle, artist);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        for (Album album : albums) {
            messageService.sendMessage(update.getMessage().getChatId().toString(), album.toString());
        }
    }
}

package command;

import model.Track;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import сlient.GeniusHttpClient;

import java.io.IOException;

public class LyricCommand implements Command {

    private final SendBotMessageService messageService;
    private final GeniusHttpClient geniusClient;

    public LyricCommand(SendBotMessageService messageService, GeniusHttpClient geniusClient) {
        this.messageService = messageService;
        this.geniusClient = geniusClient;
    }

    @Override
    public void execute(Update update) {
        String text = update.getMessage().getText();
        String[] message = text.split(" ");
        String[] albumAndArtist = message[1].split("-");

        String artistName = albumAndArtist[0];
        String artist = artistName.replace("_", " ");

        String nameAlbum = albumAndArtist[1];
        String albumTitle = nameAlbum.replace("_", " ");

        Track track = null;

        try {
            track = geniusClient.getTrack(artist, albumTitle);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        messageService.sendMessage(update.getMessage().getChatId().toString(), track.toString());

    }
}

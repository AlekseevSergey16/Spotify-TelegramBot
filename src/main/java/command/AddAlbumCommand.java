package command;

import model.Album;
import model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.AlbumService;
import service.SendBotMessageService;
import service.TelegramUserService;
import service.dbService.DBException;
import service.dbService.DBService;
import service.dbService.MediaService;
import сlient.SpotifyHttpClient;

import java.io.IOException;
import java.util.List;

public class AddAlbumCommand implements Command {

    private final SendBotMessageService messageService;
    private final SpotifyHttpClient spotifyClient;
    private final TelegramUserService userService;
    private final AlbumService albumService;
    private final MediaService mediaService;

    public AddAlbumCommand(SendBotMessageService messageService, SpotifyHttpClient spotifyClient,
                           TelegramUserService userService, AlbumService albumService, MediaService mediaService) {
        this.spotifyClient = spotifyClient;
        this.messageService = messageService;
        this.userService = userService;
        this.albumService = albumService;
        this.mediaService = mediaService;
    }

    @Override
    public void execute(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = msg.getChatId().toString();
        String text = update.getCallbackQuery().getData();
        String[] message = text.split("-");

        String title = message[2];
        String artist = message[1];
        //String artist = artistName.replace("◦", "");

        int albumId = albumService.getAlbumId(title, artist);
        List<Album> album = null;
        if (albumId == -1) {
            try {
                album = spotifyClient.getAlbums(title, artist.replace("◦", ""));
                if (album.size() > 0) {
                    albumService.saveAlbum(album.stream().filter(x ->  title.equals(x.getTitle())).findFirst().get());
                    albumId = albumService.getAlbumId(title, artist);
                    mediaService.add(chatId, albumId);
                    messageService.sendMessage(chatId, "Успех!");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        else {
            mediaService.add(chatId, albumId);
            messageService.sendMessage(chatId, "Успех!");
        }
    }
}

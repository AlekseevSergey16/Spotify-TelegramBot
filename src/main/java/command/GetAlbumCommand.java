package command;

import model.Album;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import service.dbService.MediaService;

import java.util.List;

public class GetAlbumCommand implements Command {
    private final SendBotMessageService messageService;
    private final MediaService mediaService;

    public GetAlbumCommand(SendBotMessageService messageService, MediaService mediaService) {
        this.messageService = messageService;
        this.mediaService = mediaService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<Album> albums = mediaService.getUserMedia(chatId);

        for (Album album : albums) {
            messageService.sendMessage(update.getMessage().getChatId().toString(), album.toString());
        }
    }
}

package command;

import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import service.SendBotMessageServiceImpl;

public class HelpCommand implements Command {

    private static final String HELP_MESSAGE = "/new - new releases" + "\n"
            + "/playlists - random playlists";

    private SendBotMessageService messageService;

    public HelpCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}

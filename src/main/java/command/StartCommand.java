package command;

import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import service.SendBotMessageServiceImpl;

public class StartCommand implements Command {

    private static final String START_MESSAGE = "Современный мир балует нас широким разнообразием музыки: " +
            "любители поп-музыки, рокеры, рэперы и любители клубов " +
            "сталкиваются с почти бесконечным выбором.\n" +
            "Music Advisor - музыкальный консультант, который делится ссылками на новые релизы и избранные плейлисты.\n" +
            "Он станет для вас мощным проводником в мир музыки.";

    private final SendBotMessageService messageService;

    public StartCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

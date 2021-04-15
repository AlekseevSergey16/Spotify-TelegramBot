package command;

import model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import service.dbService.DBException;
import service.dbService.DBService;

public class StartCommand implements Command {

    private final DBService dbService;

    private static final String START_MESSAGE = "Современный мир балует нас широким разнообразием музыки: " +
            "любители поп-музыки, рокеры, рэперы и любители клубов " +
            "сталкиваются с почти бесконечным выбором.\n" +
            "Music Advisor - музыкальный консультант, который делится ссылками на новые релизы и избранные плейлисты.\n" +
            "Он станет для вас мощным проводником в мир музыки.";

    private final SendBotMessageService messageService;

    public StartCommand(SendBotMessageService messageService, DBService dbService) {
        this.messageService = messageService;
        this.dbService = dbService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        TelegramUser user = null;
        try {
            user = dbService.findByChatId(chatId);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (user == null) {
            user = new TelegramUser(chatId);
            dbService.saveUser(user);
        }
        messageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

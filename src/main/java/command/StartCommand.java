package command;

import model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotMessageService;
import service.TelegramUserService;
import service.dbService.DBException;
import service.dbService.DBService;
import service.dbService.DBServiceImpl;

public class StartCommand implements Command {

    private final TelegramUserService telegramUserService;

    private static final String START_MESSAGE = "Современный мир балует нас широким разнообразием музыки: " +
            "любители поп-музыки, рокеры, рэперы и любители клубов " +
            "сталкиваются с почти бесконечным выбором.\n" +
            "Music Advisor - музыкальный консультант, который делится ссылками на новые релизы и избранные плейлисты.\n" +
            "Он станет для вас мощным проводником в мир музыки.";

    private final SendBotMessageService messageService;

    public StartCommand(SendBotMessageService messageService, TelegramUserService telegramUserService) {
        this.messageService = messageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        TelegramUser user = telegramUserService.findByChatId(chatId);
        if (user == null) {
            user = new TelegramUser(chatId);
            telegramUserService.saveUser(user);
        }
        messageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

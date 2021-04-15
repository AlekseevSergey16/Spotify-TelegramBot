package main;

import bot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import service.SendBotMessageServiceImpl;

public class Main {

    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            SendBotMessageServiceImpl messageService = new SendBotMessageServiceImpl(bot);
            messageService.sendMessage("563775458", "Привет");
            messageService.sendMessage("1015299108", "Привет, Настя! Тебя любит Серёжа <3");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

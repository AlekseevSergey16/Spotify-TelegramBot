package main;

import bot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import service.SendBotMessageServiceImpl;

public class Main {

    public static void main(String[] args) {
        String message = "Привет, я опять заработал! И теперь появилась функция, которую ждал(а) ты, теперь доступен поиск русских альбомов!\uD83C\uDFB5\uD83C\uDFB6";
        try {
            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            SendBotMessageServiceImpl messageService = new SendBotMessageServiceImpl(bot);
            //messageService.sendMessage("563775458", message);
            //messageService.sendMessage("1015299108", message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

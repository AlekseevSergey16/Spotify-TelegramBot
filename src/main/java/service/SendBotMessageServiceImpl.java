package service;

import bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final Bot spotifyBot;

    public SendBotMessageServiceImpl(Bot spotifyBot) {
        this.spotifyBot = spotifyBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            spotifyBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

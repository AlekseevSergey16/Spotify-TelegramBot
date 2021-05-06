package service;

import bot.Bot;
import model.Album;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class SendBotMessageServiceImpl implements SendBotMessageService {

    protected final Bot spotifyBot;

    public SendBotMessageServiceImpl(Bot spotifyBot) {
        this.spotifyBot = spotifyBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(getKeyboard());
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            spotifyBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String chatId, String message, Album album) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(getKeyboard());
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        String callBack = "/add -" + album.getNameArtist() + "-" + album.getTitle();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> rowButtons = new ArrayList<>();
        InlineKeyboardButton addButton = new InlineKeyboardButton("Добавить");
        addButton.setCallbackData(callBack);
        rowButtons.add(addButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(rowButtons);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            spotifyBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        List<KeyboardRow> keyBoard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton media = new KeyboardButton();
        media.setText("/media");
        row.add(media);
        keyBoard.add(row);
        keyboardMarkup.setKeyboard(keyBoard);

        return keyboardMarkup;
    }
}

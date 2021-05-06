package bot;

import command.CommandContainer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import service.SendBotMessageServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private static String BOT_TOKEN = "";
    private static String BOT_USERNAME = "MusicAdv1sorBot";

    private final CommandContainer commandContainer;

    public Bot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            if (message.startsWith("/")) {
                String commandId = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandId).execute(update);
            }
        } else if (update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();
            if (message.startsWith("/")) {
                String commandId = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandId).execute(update);
            }
        }
    }
}

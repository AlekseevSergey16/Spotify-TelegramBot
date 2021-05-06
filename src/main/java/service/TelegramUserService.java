package service;

import model.TelegramUser;
import service.dbService.DBException;

public interface TelegramUserService {
    void saveUser(TelegramUser user);
    TelegramUser findByChatId(String chatId);
}

package service;

import model.TelegramUser;
import service.dbService.DBException;
import service.dbService.DBService;
import service.dbService.DBServiceImpl;

public class TelegramUserServiceImpl implements TelegramUserService {

    private final DBService dbService;

    public TelegramUserServiceImpl() {
        dbService = DBServiceImpl.getInstance();
    }

    @Override
    public void saveUser(TelegramUser user) {
        try {
            dbService.addUser(user);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TelegramUser findByChatId(String chatId) {
        try {
            return dbService.getUser(chatId);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }
}

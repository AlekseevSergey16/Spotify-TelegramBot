package service.dbService.dao;

import model.TelegramUser;
import service.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class TelegramUserDAO {

    private final Executor executor;

    public TelegramUserDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void insert(TelegramUser user) throws SQLException {
        String query = "INSERT INTO spotify_db.spotify_storage.tg_user(chat_id) VALUES(" + user.getChatId() + ")";
        executor.execUpdate(query);
    }

    public TelegramUser get(String chatId) throws SQLException {
        String query = "SELECT chat_id FROM spotify_db.spotify_storage.tg_user WHERE chat_id = '" + chatId +"'";
        return executor.execQuery(query, resultSet -> {
            if (resultSet.next()) {
                return new TelegramUser(resultSet.getString("chat_id"));
            }
            return null;
        });
    }

}

package service.dbService;

import model.Album;
import model.TelegramUser;

import java.util.List;

public interface DBService {
    TelegramUser getUser(String chatId) throws DBException;
    void addUser(TelegramUser user) throws DBException;
    int getAlbum(String title, String artist) throws DBException;
    void addAlbum(Album album) throws DBException;
    void addMedia(String chatId, int albumId) throws DBException;
    List<Album> getMedia(String chatId) throws DBException;
    void printConnectInfo();
}

package service;

import model.Album;
import service.dbService.DBException;
import service.dbService.DBService;
import service.dbService.DBServiceImpl;
import service.dbService.MediaService;

import java.util.List;

public class MediaServiceImpl implements MediaService {

    private DBService dbService;

    public MediaServiceImpl() {
        dbService = DBServiceImpl.getInstance();
    }
    @Override
    public void add(String chatId, int albumId) {
        try {
            dbService.addMedia(chatId, albumId);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Album> getUserMedia(String chatId) {
        try {
            return dbService.getMedia(chatId);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }
}

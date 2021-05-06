package service;

import model.Album;
import service.dbService.DBException;
import service.dbService.DBService;
import service.dbService.DBServiceImpl;

public class AlbumServiceImpl implements AlbumService {

    private final DBService dbService;

    public AlbumServiceImpl() {
        dbService = DBServiceImpl.getInstance();
    }

    @Override
    public void saveAlbum(Album album) {
        try {
            dbService.addAlbum(album);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAlbumId(String title, String artist) {
        try {
            return dbService.getAlbum(title, artist);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

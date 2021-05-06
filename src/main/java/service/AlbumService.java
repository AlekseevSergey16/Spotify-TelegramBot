package service;

import model.Album;

public interface AlbumService {
    void saveAlbum(Album album);
    int getAlbumId(String title, String artist);
}

package service;

import model.Album;

public interface AlbumService {
    void saveAlbum(Album album);
    Album getAlbum(String title, String artist);
}

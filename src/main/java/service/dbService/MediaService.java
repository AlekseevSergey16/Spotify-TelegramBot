package service.dbService;

import model.Album;

import java.util.List;

public interface MediaService {
    void add(String chatId, int albumId);
    List<Album> getUserMedia(String chatId);
}

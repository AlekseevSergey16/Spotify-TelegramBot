package service.dbService.dao;

import model.Album;
import org.checkerframework.checker.units.qual.A;
import service.dbService.MediaService;
import service.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaLibraryDAO {

    private final Executor executor;

    public MediaLibraryDAO(Connection connection) {
        executor = new Executor(connection);
    }

    public void insert(String chatId, int albumId) throws SQLException {
        String query = "INSERT INTO spotify_storage.media_library (chat_id, album_id) VALUES ('"
                + chatId + "', " + albumId + ")";
        executor.execUpdate(query);
    }

    public List<Album> get(String chatId) throws SQLException {
        String query = "SELECT title, name_artist, release_date, total_tracks, url_spotify\n" +
                        "FROM spotify_storage.media_library\n" +
                            " INNER JOIN spotify_storage.tg_user ON media_library.chat_id = tg_user.chat_id\n" +
                            " INNER JOIN spotify_storage.album ON media_library.album_id = album.album_id " +
                        "WHERE media_library.chat_id ='" + chatId + "';";

        return executor.execQuery(query, resultSet -> {
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                Album album = new Album(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)
                );
                albums.add(album);
            }
            return albums;
        });
    }
}

package service.dbService.dao;

import model.Album;
import service.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class AlbumDao {
    private Executor executor;

    public AlbumDao(Connection connection) {
        executor = new Executor(connection);
    }

    public void insert(Album album) throws SQLException {
        String title = "'"+album.getTitle()+"',";
        String artist = "'"+album.getNameArtist()+"',";
        String releaseDate = "'"+album.getReleaseDate()+"',";
        String totalTracks = ""+album.getTotalTracks()+",";
        String urlSpotify = "'"+album.getUrlSpotify()+"'";

        String query = "INSERT INTO spotify_storage.album (title, name_artist, release_date, total_tracks, url_spotify) VALUES("
                + title + artist + releaseDate + totalTracks + urlSpotify + ");";
        executor.execUpdate(query);
    }

    public int get(String title, String artist) throws SQLException {
        String query = "SELECT album_id FROM spotify_storage.album WHERE title = '"
                + title +"' AND " + "name_artist = '" + artist + "'";
        return executor.execQuery(query, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt("album_id");
            }
            return -1;
        });
    }
}

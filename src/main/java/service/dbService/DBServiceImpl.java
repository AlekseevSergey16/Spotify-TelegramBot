package service.dbService;

import model.Album;
import model.TelegramUser;
import service.dbService.dao.AlbumDao;
import service.dbService.dao.MediaLibraryDAO;
import service.dbService.dao.TelegramUserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBServiceImpl implements DBService {

    private static DBService dbService;
    private final Connection connection;

    private DBServiceImpl() {
        this.connection = getPostgreSQLConnection();
        printConnectInfo();
    }

    public static DBService getInstance() {
        if (dbService == null) {
            dbService = new DBServiceImpl();
        }
        return dbService;
    }

    public void addMedia(String chatId, int albumId) {
        try {
            connection.setAutoCommit(false);
            MediaLibraryDAO mediaLibraryDAO = new MediaLibraryDAO(connection);
            mediaLibraryDAO.insert(chatId, albumId);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {}
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    public List<Album> getMedia(String chatId) throws DBException {
        try {
            return new MediaLibraryDAO(connection).get(chatId);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void addUser(TelegramUser user) {
        try {
            connection.setAutoCommit(false);
            TelegramUserDAO userDAO = new TelegramUserDAO(connection);
            userDAO.insert(user);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {}
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    @Override
    public TelegramUser getUser(String chatId) throws DBException {
        try {
            return new TelegramUserDAO(connection).get(chatId);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void addAlbum(Album album) {
        try {
            connection.setAutoCommit(false);
            AlbumDao albumDao = new AlbumDao(connection);
            albumDao.insert(album);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {}
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    @Override
    public int getAlbum(String title, String artist) throws DBException {
        try {
            return new AlbumDao(connection).get(title, artist);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static Connection getPostgreSQLConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/spotify_db?";
            String userName = "postgres";
            String password = "postgresql1607";

            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

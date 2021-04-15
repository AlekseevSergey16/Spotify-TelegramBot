package service.dbService;

import model.Album;
import model.TelegramUser;
import service.AlbumService;
import service.TelegramUserService;
import service.dbService.dao.TelegramUserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService implements TelegramUserService, AlbumService {

    private final Connection connection;

    public DBService() {
        this.connection = getPostgreSQLConnection();
        printConnectInfo();
    }

    @Override
    public void saveUser(TelegramUser user) {
        try {
            connection.setAutoCommit(false);
            TelegramUserDAO userDAO = new TelegramUserDAO(connection);
            userDAO.insertUser(user);

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
    public TelegramUser findByChatId(String chatId) throws DBException {
        try {
            return new TelegramUserDAO(connection).getUser(chatId);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void saveAlbum(Album album) {

    }

    @Override
    public Album getAlbum(String title, String artist) {
        return null;
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

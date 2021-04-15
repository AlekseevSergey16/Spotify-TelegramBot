package service.dbService.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        T value = handler.handle(resultSet);

        resultSet.close();
        statement.close();

        return value;
    }
}

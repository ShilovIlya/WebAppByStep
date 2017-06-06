package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(update);
        statement.close();
    }

    public String execQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        return resultSet.toString();
    }
}

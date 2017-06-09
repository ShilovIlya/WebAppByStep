package jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class executes updates and queries.
 */
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

    /**
     * Method for taking data from database
     *
     * @param query - query for executing
     * @return string representation of result set
     * @throws SQLException
     */
    public <T> T execQuery(String query, Handler<T> handler) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        T result = handler.handle(resultSet);
        resultSet.close();
        statement.close();
        return result;
    }
}

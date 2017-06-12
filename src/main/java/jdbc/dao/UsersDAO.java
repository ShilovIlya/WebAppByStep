package jdbc.dao;

import datasets.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for encapsulating database query logic
 */
public class UsersDAO {
    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public User getUserById(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id,
                resultSet -> new User(resultSet.getLong(0), resultSet.getString(1),
                         resultSet.getString(2), resultSet.getString(3)));
    }

    public User getUserByLogin(String login) throws SQLException{
        return executor.execQuery("select * from users where login=\"" + login + "\"",
                resultSet -> new User(resultSet.getLong(0), resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3)));
    }

    public long getIdByLogin(String login) throws SQLException{
        return executor.execQuery("select * from users where users.login='" + login + "')",
                resultSet -> resultSet.getLong(0));
    }

    public void insertUser(String login, String password, String email) throws SQLException {
        executor.execUpdate("insert into users values ('" + login + "," + password + "," + email + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(30), " +
                "password varchar(30), email varchar(30), primary key (id))");
    }

    public String selectAll() throws SQLException {
        return executor.execQuery("select * from users",(resultSet) -> {
            StringBuilder result = new StringBuilder("");
            while (resultSet.next()) {
                result.append(resultSet.getInt("id"));
                result.append(resultSet.getString("login"));
                result.append(resultSet.getString("password"));
                result.append(resultSet.getString("email"));
                result.append("\"");
            }
            return result.toString();
        });
    }

    public String describeTable() throws SQLException {
        return executor.execQuery("show columns from users", (resultSet) -> {
            StringBuilder result = new StringBuilder("");
            while (resultSet.next()) {
                result.append(resultSet.getInt("id"));
                result.append(resultSet.getString("login"));
                result.append(resultSet.getString("password"));
                result.append(resultSet.getString("email"));
                result.append("\"");
            }
            return result.toString();
        });
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}

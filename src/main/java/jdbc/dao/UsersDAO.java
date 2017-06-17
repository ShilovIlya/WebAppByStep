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
                (resultSet) -> {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong(0), resultSet.getString(1),
                                resultSet.getString(2), resultSet.getString(3));
                    }
                    return null;
                }
        );
    }

    public User getUserByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where login=\'" + login + "\'",
                (resultSet) -> {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("login"),
                                resultSet.getString("password"), resultSet.getString("email"));
                    } else {
                        return null;
                    }
                });
    }

    public long getIdByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where login=\'" + login + "\'",
                (resultSet) -> {
                    if (resultSet.next()) {
                        return resultSet.getLong(1);
                    } else {
                        return -2L;
                    }
                });
    }

    public void insertUser(String login, String password, String email) throws SQLException {
        executor.execUpdate("insert into users (login, password, email) values ('" + login + "','" + password + "','" + email + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (" +
                "id bigint auto_increment, " +
                "login varchar(30), " +
                "password varchar(30), " +
                "email varchar(30), " +
                "primary key (id))");
    }

    public String selectAll() throws SQLException {
        return executor.execQuery("select * from users", (resultSet) -> {
            StringBuilder result = new StringBuilder("");
            while (resultSet.next()) {
                result.append(resultSet.getInt("id"))
                        .append(" ")
                        .append(resultSet.getString("login"))
                        .append(" ")
                        .append(resultSet.getString("password"))
                        .append(" ")
                        .append(resultSet.getString("email"))
                        .append(" ; ");
            }
            return result.toString();
        });
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}

package dao;

import db.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {
    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public String getUserById(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id);
    }

    public String getIdByLogin(String login) throws SQLException{
        return executor.execQuery("select * from users where login='" + login + "')");
    }

    public void insertUser(String login, String password, String email) throws SQLException {
        executor.execUpdate("insert into users values ('" + login + "," + password + "," + email + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(30), password varchar(30), email varchar(30), primary key (id)");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}

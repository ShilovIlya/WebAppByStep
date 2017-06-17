package jdbc.dbservice;

import datasets.User;
import jdbc.dao.UsersDAO;
import services.DBService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for encapsulating database connection management
 */
public class JDBCService implements DBService {
    private final Connection connection;

    public JDBCService() {
        this.connection = getH2Connection();
    }

    public void initialize() {
        try {
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for saving new user to table users.
     * Create new table and try insert new user.
     * Manage commit and rollback.
     *
     * @param login - user login
     * @param password - user password
     * @param email - user email
     * @return new user id
     */
    public long addUser(String login, String password, String email) {
        long userId = -1;
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.insertUser(login, password, email);
            System.out.println("Hello JDBC " + dao.selectAll());
            connection.commit();
            userId = dao.getIdByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
        return userId;
    }

    public User getUserByLogin(String login) {
        try {
            UsersDAO usersDAO = new UsersDAO(connection);
            return usersDAO.getUserByLogin(login);
        } catch (SQLException e) {
            //TODO: add logger
            System.out.println("Login is not exist.");
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(long id) {
        try {
            UsersDAO usersDAO = new UsersDAO(connection);
            return usersDAO.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUp() {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./test";
            String name = "wais";
            String password = "wais";

            Connection connection = DriverManager.getConnection(url, name, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

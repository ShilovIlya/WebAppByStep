package hibernate.dbservice;

import datasets.User;
import hibernate.dao.UsersDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import services.DBService;

import java.sql.Connection;
import java.sql.SQLException;

public  class HibernateDBService implements DBService{
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public HibernateDBService() {
        Configuration configuration = getH2Configuration();
        this.sessionFactory = createSessionFactory(configuration);
    }

    public long addUser(String login, String password, String email) {
        long userId = -1;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            userId = dao.insertUser(login, password, email);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public User getUserByLogin(String login) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            User user = dao.getUserByLogin(login);
            session.close();
            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(long id) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            User user = dao.getUserById(id);
            session.close();
            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./test");
        configuration.setProperty("hibernate.connection.username", "wais");
        configuration.setProperty("hibernate.connection.password", "wais");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void cleanUp() {

    }

    public void initialize() {

    }
}

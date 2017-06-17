package services;

import datasets.User;

public interface DBService {

    public long addUser(String login, String password, String email);

    public User getUserByLogin(String login);

    public User getUserById(long id);

    public void printConnectInfo();

    public void cleanUp();

    public void initialize();

}

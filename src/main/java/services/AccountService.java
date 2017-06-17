package services;

import datasets.User;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    DBService dbService;
    private final Map<String, User> sessionIdToUser;

    public AccountService(DBService dbService) {
        this.dbService = dbService;
        this.sessionIdToUser = new HashMap<>();
    }

    public Long addNewUser(String login, String password, String email) {
        return dbService.getUserByLogin(login) == null ? dbService.addUser(login, password, email) : null;
    }

    public User getUserByLogin(String login) {
        return dbService.getUserByLogin(login);
    }

    public User getUserBySessionId(String sessionId) {
        return sessionIdToUser.get(sessionId);
    }

    public void addSession(String sessionId, User user) {
        sessionIdToUser.put(sessionId, user);
    }

    public void deleteSession(String sessionId) {
        sessionIdToUser.remove(sessionId);
    }
}

package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, User> loginToUser;
    private final Map<String, User> sessionIdToUser;


    public AccountService() {
        this.loginToUser = new HashMap<>();
        this.sessionIdToUser = new HashMap<>();
    }

    public void addNewUser(User user) {
        loginToUser.put(user.getLogin(), user);
    }

    public User getUserByLogin(String login) {
        return loginToUser.get(login);
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

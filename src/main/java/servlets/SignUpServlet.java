package servlets;

import accounts.AccountService;
import accounts.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet{
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (login == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User user = accountService.getUserByLogin(login);
        //if user already exist
        if (user != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User newUser = new User(login, password, email);
        accountService.addNewUser(newUser);
        accountService.addSession(request.getSession().getId(), user);
        Gson gson = new Gson();
        String json = gson.toJson(newUser);
        response.getWriter().print(json);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
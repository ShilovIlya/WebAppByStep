package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Hello World with doGet! Session Id = " + sessionId);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Hello World with doPost! Session Id = " + sessionId);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

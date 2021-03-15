package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;


public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to /WEB-INF/<the correct page>.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        request.setAttribute("message", "");
        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Did post request at LoginServlet");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        int loginResult =
                0;
        try {
            loginResult = database_handler.UserInfoHandler.checkCredentials(username,
            password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (loginResult > 0) {
            String message = "Login was successful!";
            System.out.println(message);
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("userId", loginResult);

            response.sendRedirect("predict");

        }
        else{
            String message = "Login was not successful. Please try again.";
            System.out.println(message);
            out.print("Sorry, username or password error!");
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/");
            dispatcher.forward(request, response);
        }
        out.close();
    }
}

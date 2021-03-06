package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to /WEB-INF/<the correct page>.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)

        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        String username = ""; //get from request/response
        String password = ""; //get from request/response

        if (database_handler.UserInfoHandler.checkCredentials(username,
                password) == 0) {
//            return "pagina met yes het lukte";
        } else {
//            return "pagina met shit verkeerd wachtwoord"
        }
    }
}

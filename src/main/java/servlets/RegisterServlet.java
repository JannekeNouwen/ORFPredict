package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Forward to /WEB-INF/<the correct page>.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if (password.equals(password2)) {
            int status = 0;
            try {
                status = database_handler.UserInfoHandler.newUser(username,
                        password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (status == 0) {
                String message = "Your account was successfully created. " +
                        "Please use your new credentials to login.";
                request.setAttribute("message", message);
                RequestDispatcher dispatcher =
                        this.getServletContext().getRequestDispatcher(
                                "/");
                dispatcher.forward(request, response);
            } else {
                String message = "This username is already taken.";
                request.setAttribute("message", message);
                RequestDispatcher dispatcher =
                        this.getServletContext().getRequestDispatcher(
                                "/register.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            String message = "Your passwords do not match.";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/register.jsp");
            dispatcher.forward(request, response);
        }

    }
}
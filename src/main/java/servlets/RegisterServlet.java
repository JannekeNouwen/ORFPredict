package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

// TODO: documentatie toevoegen.
public class RegisterServlet extends HttpServlet {
    // TODO: documentatie toevoegen.
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

    // TODO: documentatie toevoegen.
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username").strip();
        String password1 = request.getParameter("password").strip();
        String password2 = request.getParameter("password2").strip();

        String message;
        RequestDispatcher dispatcher;
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

        if (password1.equals(password2)) {
            if (!passwordPattern.matcher(password1).find()) {
                message = "Password does not match the requirements!";
                request.setAttribute("message", message);
                dispatcher = this.getServletContext().getRequestDispatcher("/register.jsp");
            } else {
                int status = 0;
                try {
                    status = database_handler.UserInfoHandler.newUser(username,
                            password1);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (status == 0) {
                    message = "Your account was successfully created. " +
                            "Please use your new credentials to login.";
                    request.setAttribute("message", message);
                    dispatcher =
                            this.getServletContext().getRequestDispatcher(
                                    "/");
                } else {
                    message = "This username is already taken!";
                    request.setAttribute("message", message);
                    dispatcher =
                            this.getServletContext().getRequestDispatcher(
                                    "/register.jsp");
                }
            }
        } else {
            message = "Password confirmation does not match password!";
            request.setAttribute("message", message);
            dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/register.jsp");
        }
        dispatcher.forward(request, response);

    }
}

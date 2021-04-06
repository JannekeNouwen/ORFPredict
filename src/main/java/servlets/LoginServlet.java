package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class LoginServlet
 *
 * A class which processes incoming requests from
 * clients to show a page where a user can log into
 * the website.
 *
 * @version 1
 * @author Yuri, Janneke & Max
 * */
public class LoginServlet extends HttpServlet {
    /**
     * A method which handles get-requests from clients. Shows
     * the user the form to login int their account.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
//        try {
        if (session.getAttribute("userId") == null) {
            // Forward to /WEB-INF/<the correct page>.jsp
            // (Users can not access directly into JSP pages placed in WEB-INF)
            if (session.getAttribute("message") != "") {
                request.setAttribute("message", session.getAttribute("message"));
                session.setAttribute("message", "");
            } else {
                request.setAttribute("message", "");
            }
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/login.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                if (request.getParameter("logout").equals("true")) {
                    session.invalidate();
                    response.sendRedirect("/");
                }
            } catch ( NullPointerException ignore) {}
            request.setAttribute("message", "");
            session.setAttribute("output_file", "");
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
//            response.sendRedirect("predict");
        }
    }

    /**
     * A method which handles post-requests from clients. Retrieves
     * the credentials from the form and tries to log the user
     * into their account.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("register".equals(action)) {
            response.sendRedirect("register");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            int loginResult =
                    0;
            try {
                loginResult = database_handler.UserInfoHandler.checkCredentials(username,
                        password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            HttpSession session = request.getSession();
            if (loginResult > 0) {
                String message = "Login was successful!";
                session.setAttribute("username", username);
                session.setAttribute("userId", loginResult);

                response.sendRedirect("predict");

            } else {
                String message = "Login was not successful. Please try again.";
                session.setAttribute("message", message);

                response.sendRedirect("login");

            }
        }
    }
}

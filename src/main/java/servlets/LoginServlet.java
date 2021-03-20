package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {
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
            request.setAttribute("message", "");
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
//            response.sendRedirect("predict");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Did post request at LoginServlet");

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
                System.out.println(message);
                session.setAttribute("username", username);
                System.out.println(session.getAttribute("username"));
                session.setAttribute("userId", loginResult);
                System.out.println(session.getAttribute("userId"));

                response.sendRedirect("predict");

            } else {
                String message = "Login was not successful. Please try again.";
                System.out.println(message);
                session.setAttribute("message", message);

                response.sendRedirect("login");
//                RequestDispatcher dispatcher =
//                        this.getServletContext().getRequestDispatcher(
//                                "/");
//                dispatcher.forward(request, response);
            }
        }
    }
}

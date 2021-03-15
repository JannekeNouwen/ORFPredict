package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PredictServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session.getAttribute("userId") == null) {
            // No session created yet, so user is not logged in
            request.setAttribute("message", "You have to log in before you " +
                    "can use this page.");
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/");
            dispatcher.forward(request, response);
        } else {
            // Forward to /WEB-INF/<the correct page>.jsp
            // (Users can not access directly into JSP pages placed in WEB-INF)
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Did post request at PredictServlet");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username);

        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/predict.jsp");
        dispatcher.forward(request, response);
    }
}
package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class ResultHistoryServlet
 *
 * A class which processes incoming requests from
 * clients to show a list of all ORF-searches the
 * user has submitted.
 *
 * @version 1
 * @author Yuri, Janneke & Max
 * */
public class ResultHistoryServlet extends HttpServlet{
    /**
     * A method which handles get-requests from clients. Shows
     * a list of all ORF-searches the user has submitted.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        // TODO: Sorting functionality for resultHistory
        HttpSession session = request.getSession(false);
        try {
            if (session.getAttribute("userId") == null) {
                // No session created yet, so user is not logged in
                request.setAttribute("message", "You have to log in before you " +
                        "can use this page.");
                RequestDispatcher dispatcher =
                        this.getServletContext().getRequestDispatcher(
                                "/");
                dispatcher.forward(request, response);
            } else {
                ArrayList<ArrayList<String>> resultSummary =
                        null;
                try {
                    resultSummary = database_handler.DatabaseHandler.getResultSummary(
                            (Integer) session.getAttribute("userId"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                request.setAttribute("resultSummary", resultSummary);
                // Forward to /WEB-INF/<the correct page>.jsp
                // (Users can not access directly into JSP pages placed in WEB-INF)
                RequestDispatcher dispatcher =
                        this.getServletContext().getRequestDispatcher(
                                "/resulthistory.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NullPointerException e) {
            request.setAttribute("message", "You have to log in before you " +
                    "can use this page.");
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/");
            dispatcher.forward(request, response);
        }
    }
}

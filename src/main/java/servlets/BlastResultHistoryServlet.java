package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class BlastResultHistoryServlet
 *
 * A class which processes incoming requests from
 * clients to present a list of all BLAST-searches
 * a client has executed.
 *
 * @version 1
 * @author Yuri, Janneke & Max
 * */
public class BlastResultHistoryServlet extends HttpServlet {
    /**
     * A method which handles get-requests from clients.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

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
                int orfId = Integer.parseInt(request.getParameter("orf_id"));

                ArrayList<ArrayList<String>> blastResultSummary =
                        null;
                try {
                    blastResultSummary = database_handler.DatabaseHandler.getAllBlastSearches(
                            orfId);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }

                request.setAttribute("blastResultSummary", blastResultSummary);


                // Forward to /WEB-INF/<the correct page>.jsp
                // (Users can not access directly into JSP pages placed in WEB-INF)
                RequestDispatcher dispatcher =
                        this.getServletContext().getRequestDispatcher(
                                "/blastresulthistory.jsp");
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

    /**
     * A method which handles post-requests from clients.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {



        // Add BlastResults to request or response
    }
}

package servlets;

import blast_handler.BlastResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Class BlastResultServlet
 *
 * A class which processes incoming requests from
 * clients to present a single BLAST-result to the
 * client.
 *
 * @version 1
 * @author Yuri, Janneke & Max
 * */
public class BlastResultServlet extends HttpServlet {
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
                int blastSearchId = 0;
                try {
                    blastSearchId = (int) request.getAttribute(
                            "blastsearch_id");
                } catch (NullPointerException | NumberFormatException ignore) {}
                try {
                    blastSearchId = Integer.parseInt(request.getParameter(
                            "blastsearch_id"));
                } catch (NullPointerException | NumberFormatException ignore) {}

                ArrayList<BlastResult> blastResults =
                        database_handler.
                DatabaseHandler.getBlastResult(blastSearchId);

                request.setAttribute("blastResults", blastResults);

                // Forward to /WEB-INF/<the correct page>.jsp
                // (Users can not access directly into JSP pages placed in WEB-INF)
                RequestDispatcher dispatcher =
                        this.getServletContext().getRequestDispatcher(
                                "/blastresult.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NullPointerException | ClassNotFoundException e) {
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
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

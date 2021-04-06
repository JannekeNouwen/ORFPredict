package servlets;

import orf_processing.ORFResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Class ResultServlet
 *
 * A class which processes incoming requests from
 * clients to show the results of a specific ORF-
 * search.
 *
 * @version 1
 * @author Yuri, Janneke & Max
 * */
public class ResultServlet extends HttpServlet {
    /**
     * A method which handles get-requests from clients. Shows
     * the results of a specific ORF-search.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
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
                int resultId;
                try {
                    resultId = Integer.parseInt(request.getParameter("result_id"));
                } catch (NullPointerException | NumberFormatException e) {
                    resultId = (int) session.getAttribute("result_id");
                    session.removeAttribute("result_id");
                }
                try {
                    ORFResult result =
                            database_handler.DatabaseHandler.getResult(resultId);

                    request.setAttribute("ORFArray", result.getORFs());
                    if (result.getORFs().size() == 0) {
                        RequestDispatcher dispatcher =
                                this.getServletContext().getRequestDispatcher(
                                        "/noresult.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        request.setAttribute("result", result);
                        request.setAttribute("formattedORFs",
                                result.getFormattedORFs());

                        RequestDispatcher dispatcher =
                                this.getServletContext().getRequestDispatcher(
                                        "/result.jsp");
                        dispatcher.forward(request, response);
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("message", "You have to log in before you " +
                    "can use this page.");
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/");
            dispatcher.forward(request, response);
        }
    }

    /**
     * A method which handles post-requests from clients. Handles
     * requests to execute a BLAST-search or to view BLAST-search-
     * results for this ORF-search.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/predict.jsp");
        dispatcher.forward(request, response);
    }
}
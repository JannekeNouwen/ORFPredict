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
import java.util.HashMap;


public class BlastResultServlet extends HttpServlet {
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
                int blastSearchId = Integer.parseInt(request.getParameter(
                        "blastsearch_id"));

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

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        HashMap<String, String> blastQuery = new HashMap<String, String>();

//        String XMLpath = blast_handler.BlastProcessor.blast(blastQuery);

//        ArrayList<BlastResult> BlastResults =
//                blast_handler.BlastProcessor.parseXML(XMLpath);

        // Add BlastResults to request or response
    }
}
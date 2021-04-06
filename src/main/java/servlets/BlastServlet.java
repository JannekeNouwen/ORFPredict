package servlets;

import blast_handler.BlastResult;
import orf_processing.ORF;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class BlastServlet
 * <p>
 * A class which processes incoming requests from
 * clients to show an input form from which a user
 * can start a BLAST-search.
 *
 * @author Yuri, Janneke & Max
 * @version 1
 */
public class BlastServlet extends HttpServlet {
    /**
     * A method which handles get-requests from clients.
     *
     * @param request  Incoming request from the client.
     * @param response Outgoing response to the client.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        boolean blastDone = false;
        int timeout = -1;

        // Check if the request contains a timeout variable. If this is
        // true, the user has just entered a blast query and is waiting
        // for the result
        try {
            timeout = (int) request.getAttribute("timeout");
            if (timeout >= 0) {
                blastDone = true;
            }
        } catch (NullPointerException | NumberFormatException ignore) {
        }
        try {
            timeout = Integer.parseInt(request.getParameter("timeout"));
            if (timeout >= 0) {
                blastDone = true;
            }
        } catch (NullPointerException | NumberFormatException ignore) {
        }

        // If the user is waiting for blast result, a check is done to
        // check if the output file is ready. If not, the user is sent
        // back to the waiting page. If the result is ready, the output
        // is parsed.
        if (blastDone) {
            try {
                HttpSession session = request.getSession(false);
                String outputFileName = (String) session.getAttribute(
                        "output_file");
                File f = new File(outputFileName);
                if (!f.exists()) {
                    timeout += 5;
                    request.setAttribute("timeout", timeout);
                    session.setAttribute("output_file", outputFileName);
                    RequestDispatcher dispatcher =
                            this.getServletContext().getRequestDispatcher(
                                    "/waitforblast.jsp");
                    dispatcher.forward(request, response);
                } else {
                    ArrayList<BlastResult> BlastResults =
                            blast_handler.BlastProcessor.parseXML((String) session.getAttribute("output_file"));

                    session.setAttribute("output_file", "hello");

                    int searchId =
                            Integer.parseInt(session.getAttribute("blastsearch_id").toString());
                    database_handler.DatabaseHandler.saveBlastResult(BlastResults, searchId);

                    request.setAttribute("blastsearch_id", searchId);
                    RequestDispatcher dispatcher =
                            this.getServletContext().getRequestDispatcher(
                                    "/blastresult");
                    dispatcher.forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!blastDone) {
            request.setAttribute("orf_id", request.getParameter("orf_id"));

            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/blast.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * A method which handles post-requests from clients. Values
     * from the form are retrieved and used to start a BLAST-
     * search.
     *
     * @param request  Incoming request from the client.
     * @param response Outgoing response to the client.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        HashMap<String, String> blastQuery = new HashMap<>();

        try {
            ORF orf =
                    database_handler.DatabaseHandler.getOrf(request.getParameter("orf_id"));

            blastQuery.put("database", request.getParameter("database"));
            blastQuery.put("program", request.getParameter("program"));
            blastQuery.put("evalue", request.getParameter("evalue"));
            blastQuery.put("word_size", request.getParameter("word_size"));
            blastQuery.put("alignment_number", request.getParameter("alignment_number"));
            blastQuery.put("seq", orf.getSeq().toUpperCase());

            HttpSession session = request.getSession();
            LocalDateTime now = LocalDateTime.now();
            blastQuery.put("output_file", session.getAttribute("userId") + "_" + orf.getId() + "_" + now.toString().replace(":", "_") + ".json");

            String JSONpath = blast_handler.BlastProcessor.blast(blastQuery);

            int searchId =
                    database_handler.DatabaseHandler.saveBlastSearch(blastQuery,
                            orf.getId());

            int timeout = 0;
            request.setAttribute("timeout", timeout);
            session.setAttribute("blastsearch_id", searchId);
            session.setAttribute("output_file", JSONpath);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/waitforblast.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
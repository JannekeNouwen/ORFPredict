package servlets;

import blast_handler.BlastResult;
import orf_processing.ORF;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class BlastServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Forward to /WEB-INF/<the correct page>.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        System.out.println("Dit get request at BlastServlet");
        request.setAttribute("orf_id", request.getParameter("orf_id"));
        System.out.println(request.getAttribute("orf_id"));

        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/blast.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        HashMap<String, String> blastQuery = new HashMap<>();

        try {
            System.out.println(request.getParameter("database"));
            System.out.println(request.getParameter("evalue"));
            System.out.println(request.getParameter("orf_id"));
            ORF orf =
                    database_handler.DatabaseHandler.getOrf(request.getParameter("orf_id"));

            blastQuery.put("database", request.getParameter("database"));
            blastQuery.put("program", request.getParameter("program"));
            blastQuery.put("evalue", request.getParameter("evalue"));
            blastQuery.put("organism", request.getParameter("organism"));
            blastQuery.put("exclude", request.getParameter("exclude"));
            blastQuery.put("word_size", request.getParameter("word_size"));
            blastQuery.put("alignment_number", request.getParameter("alignment_number"));
            blastQuery.put("seq", orf.getSeq());

            HttpSession session = request.getSession();
            LocalDateTime now = LocalDateTime.now();
            blastQuery.put("output_file", session.getAttribute("userId") + "_" + orf.getId() + "_" + now + ".xml");

            String XMLpath = blast_handler.BlastProcessor.blast(blastQuery);

            ArrayList<BlastResult> BlastResults =
                    blast_handler.BlastProcessor.parseXML(XMLpath);

            request.setAttribute("blastresults", BlastResults);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/blastresult.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add BlastResults to request or response
    }
}
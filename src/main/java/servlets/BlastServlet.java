package servlets;

import blast_handler.BlastResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;


//TODO: documentatie toevoegen.
public class BlastServlet extends HttpServlet {
    //TODO: documentatie toevoegen.
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Forward to /WEB-INF/<the correct page>.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/blast.jsp");
        dispatcher.forward(request, response);
    }

    //TODO: documentatie toevoegen.
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        HashMap<String, String> blastQuery = new HashMap<String, String>();


        String XMLpath = blast_handler.BlastProcessor.blast(blastQuery);

        ArrayList<BlastResult> BlastResults =
                blast_handler.BlastProcessor.parseXML(XMLpath);

        request.setAttribute("blastresults", BlastResults);
        // Add BlastResults to request or response
    }
}
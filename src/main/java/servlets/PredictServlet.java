package servlets;

import database_handler.DatabaseHandler;
import orf_processing.ORFResult;
import orf_processing.Prediction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class PredictServlet
 *
 * A class which processes incoming requests from
 * clients to show a form a user can use to start
 * a new ORF-search.
 *
 * @version 1
 * @author Yuri, Janneke & Max
 * */
@MultipartConfig
public class PredictServlet extends HttpServlet {
    /**
     * A method which handles get-requests from clients. Shows
     * the user a form to enter parameters for the ORF-search.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
//        try {
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
//        } catch (NullPointerException e) {
//            request.setAttribute("message", "You have to log in before you " +
//                    "can use this page.");
//            RequestDispatcher dispatcher =
//                    this.getServletContext().getRequestDispatcher(
//                            "/");
//            dispatcher.forward(request, response);
//        }
    }

    /**
     * A method which handles post-requests from clients. Retrieves
     * parameters from the form and tries to start an ORF-search.
     * @param request Incoming request from the client.
     * @param response Outgoing response to the client.
     * */
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Did post request at PredictServlet");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        int userId = (int) session.getAttribute("userId");
        String queryName = request.getParameter("query_name");

        int minSize = Integer.parseInt(request.getParameter("minSize"));
        String startCodon = request.getParameter("startcodon");
        String stopCodon = request.getParameter("stopcodon");

        String textInput = request.getParameter("textInput");

        // Read input from file
        Part filePart = request.getPart("fileInput");
        InputStream inputFileContent = filePart.getInputStream();
        String fileContent = null;
        try (Scanner scanner = new Scanner(inputFileContent)) {
            fileContent = scanner.useDelimiter("\\A").next();
        } catch (NoSuchElementException ignored) {
        }

        String inputSeq = null;
        if (textInput.isEmpty() && fileContent == null) {
            String message = "Please input either a fasta seq, fasta file or accession code";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
        } else if (textInput.isEmpty()) {
            inputSeq = fileContent;
        } else inputSeq = Objects.requireNonNullElse(fileContent, textInput);

        assert inputSeq != null;
        if (inputSeq.length() > 50000) {
            String message = "Please input a sequence of less then 50000";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
        }

        int resultId = 0;
        Prediction prediction = new Prediction(inputSeq, minSize, startCodon, stopCodon, userId, queryName);
        if (prediction.getType().equals("acccode")) {
            String message = "Accession codes are not yet accepted, this is a planned function";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
        } else if (!prediction.getType().equals("invalid")) {
            System.out.println("Prediction started");
            ORFResult result = prediction.predictSeq();
            System.out.println("Prediction ended");
            try {
                resultId = DatabaseHandler.saveResultToDb(result);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("going to brazil");
            session.setAttribute("result_id", resultId);
            response.sendRedirect("result");
        } else {
            String message = "Input is not valid";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
        }
    }
}
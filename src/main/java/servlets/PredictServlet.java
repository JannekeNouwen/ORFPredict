package servlets;

import orf_processing.Prediction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//TODO: documentatie toevoegen.
@MultipartConfig
public class PredictServlet extends HttpServlet {

    //TODO: documentatie toevoegen.
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

    //TODO: documentatie toevoegen.
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Did post request at PredictServlet");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username);

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

        Prediction prediction = new Prediction(inputSeq, minSize, startCodon, stopCodon);
        if (!prediction.getType().equals("invalid")) {
            prediction.predictSeq();
        } else {
            String message = "Input is not valid";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher =
                    this.getServletContext().getRequestDispatcher(
                            "/predict.jsp");
            dispatcher.forward(request, response);
        }

        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/result.jsp");
        dispatcher.forward(request, response);
    }
}
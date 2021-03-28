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


public class ResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("did GET at ResultServlet");
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
                int resultId = Integer.parseInt(request.getParameter("result_id"));
                System.out.println("Got result_id = " + resultId + " during GET request");
                try {
                    ORFResult result =
                            database_handler.DatabaseHandler.getResult(resultId);
                    System.out.println("Found result!");

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
                        System.out.println("Acc_code: " + result.getHeader());

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

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username);

        RequestDispatcher dispatcher =
                this.getServletContext().getRequestDispatcher(
                        "/predict.jsp");
        dispatcher.forward(request, response);
    }
}
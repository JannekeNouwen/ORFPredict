package database_handler;

import blast_handler.BlastResult;
import orf_processing.ORFResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHandler {
    public static void saveResultToDb(ORFResult result) {
        // save ORF prediction result to database
    }

    public static ArrayList<ArrayList<String>> getResultSummary(int userId) {
        ArrayList<ArrayList<String>> resultSummary =
                new ArrayList<>();

        // get result history summary from database

        return resultSummary;
    }

    public static ORFResult getResult(int userId) {
        ORFResult result = new ORFResult("", "", 0);  // heb ff lege data
        // ingevuld om error te voorkomen

        // get ORF result from database by user id

        return result;
    }

    public static void saveBlastResult(ArrayList<BlastResult> blastResults) {
        // ORF id ophalen door de sequentie (seq van BlastResult)?

        // Save blast results of ORF to database

    }

    public static ArrayList<ArrayList<String>> getAllBlastResults(int ORFid) {
        ArrayList<ArrayList<String>> blastResultSummary =
                new ArrayList<>();

        // get summary of all blastresults (by ORF id?)???? ik weet ff niet
        // meer wat deze functie nou moest doen

        return blastResultSummary;
    }

    public static ArrayList<BlastResult> getBlastResult(int userId) {
        ArrayList<BlastResult> blastResults = new ArrayList<>();

        // get blast results by ORF id

        return blastResults;
    }

    protected static Connection connect() throws ClassNotFoundException {
        String MySQLURL = "jdbc:mysql://165.232.120.23:3306/";
        String databseUserName = "course7user";
        String databasePassword = "ORFfound!01";

//        Class.forName("com.mysql.jdbc.Driver");
        Connection con = null;
        try {
            con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
            if (con != null) {
                String query = "use ORFPredict;";
                Statement use = con.createStatement();
                ResultSet rs1 = use.executeQuery(query);
                System.out.println("Database connection is successful!");
                return con;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

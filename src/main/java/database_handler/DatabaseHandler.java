package database_handler;

import blast_handler.BlastResult;
import orf_processing.ORFResult;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
    public static void saveResultToDb(ORFResult result) {
        // save ORF prediction result to database
    }

    /**
     * Get result history summary from database
     * @param userId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ArrayList<ArrayList<String>> getResultSummary(int userId) throws ClassNotFoundException, SQLException {
        ArrayList<ArrayList<String>> resultSummary =
                new ArrayList<>();

        Connection con = connect();
        assert con != null;
        Statement use = con.createStatement();

        String query = "select id, name, seq, acc_code, header from " +
                "orf_prediction where user_id = " + userId + ";";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ArrayList<String> newResult = new ArrayList<>();
                newResult.add(rs.getString("id"));
                newResult.add(rs.getString("name"));
                newResult.add(rs.getString("seq"));
                newResult.add(rs.getString("acc_code"));
                newResult.add(rs.getString("header"));

                resultSummary.add(newResult);
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        Class.forName("com.mysql.cj.jdbc.Driver");
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

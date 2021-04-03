package database_handler;

import blast_handler.BlastResult;
import orf_processing.ORF;
import orf_processing.ORFResult;

import java.sql.*;
import java.util.ArrayList;

/**
 * Handles all actions that need to interact with the database
 * @version 1
 * @author Yuri, Janneke & Max
 */
public class DatabaseHandler {
    /**
     * Save ORF prediction result to database
     * @param result result of ORF prediction
     * @return id of saved result
     * @throws ClassNotFoundException when no suitable driver is found
     */
    public static int saveResultToDb(ORFResult result) throws ClassNotFoundException {
        Connection con = connect();
        assert con != null;

        String header = "";
        try {
            header = result.getHeader().replace("'", "\\'");
        } catch (NullPointerException ignore) {}

        String name = result.getName().replace("'", "\\'");

        // Voeg predictie toe aan database
        try {
            String query = "insert into orf_prediction(name, seq, " +
                    "user_id, header) values ('" +
                    name + "', '" +
                    result.getSeq() + "', " +
                    result.getUserId() + ", '" +
                    header + "' " +
                    ");";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);

            query = "select id from " +
                    "orf_prediction where seq = '" + result.getSeq() + "' " +
                    " and name = '" + name +
                    "' and user_id = " + result.getUserId() + " " +
                    "order by id desc;";

            // Voeg alle gevonden ORFs toe aan de database
            try (Statement stmt2 = con.createStatement()) {
                ResultSet rs = stmt2.executeQuery(query);
                rs.next();
                int id = rs.getInt("id");
                ArrayList<ORF> orfs = result.getORFs();
                for (ORF orf : orfs) {
                    query = "insert into orf(seq, orf_prediction_id, " +
                            "start_pos, reading_frame) values ('" +
                            orf.getSeq() + "', " +
                            id + ", " +
                            orf.getStart() + ", " +
                            orf.getReadingFrame() + " " +
                            ");";
                    stmt = con.createStatement();
                    stmt.executeUpdate(query);
                }
                con.close();
                return id;

            } catch (SQLException e) {
                e.printStackTrace();
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. Please try again.");
        }
        return 0;
    }

    /**
     * Get result history summary from database
     * @param userId user id of the user who's result summary is requested
     * @return ArrayList of result history
     * @throws ClassNotFoundException when no suitable driver is found
     */
    public static ArrayList<ArrayList<String>> getResultSummary(int userId) throws ClassNotFoundException {
        ArrayList<ArrayList<String>> resultSummary =
                new ArrayList<>();

        Connection con = connect();
        assert con != null;

        // Get a summary of all the results from a user
        String query = "select id, name, seq, header from " +
                "orf_prediction where user_id = " + userId + ";";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String seq = rs.getString("seq");
                if (seq.length() > 20) {
                    seq = seq.substring(0, 18) + "...";
                }
                // Add arraylist of info for one prediction to the result
                // summary
                ArrayList<String> newResult = new ArrayList<>();
                newResult.add(rs.getString("id"));
                newResult.add(rs.getString("name"));
                newResult.add(seq);
                newResult.add(rs.getString("header"));

                resultSummary.add(newResult);
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSummary;
    }

    /**
     * get ORF result from database by user id
     * @param resultId id of the result needed from the database
     * @return ORFResult object containing all information about the prediction
     * @throws ClassNotFoundException when no suitable driver is found
     */
    public static ORFResult getResult(int resultId) throws ClassNotFoundException, SQLException {

        Connection con = connect();
        assert con != null;

        // Get all information needed for ORFResult object
        String query = "select name, seq, user_id, header from " +
                "orf_prediction where id = " + resultId + ";";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        ORFResult result = new ORFResult(
                rs.getString("seq"),
                rs.getString("name"),
                rs.getInt("user_id"),
                rs.getString("header")
        );

        // Get all information for ORFs belonging to the prediction
        query = "select id, seq, start_pos, reading_frame from " +
                "orf where orf_prediction_id = " + resultId + ";";
        Statement stmt2 = con.createStatement();
        rs = stmt2.executeQuery(query);
        while (rs.next()) {
            ORF orf = new ORF(
                    rs.getInt("id"),
                    rs.getInt("start_pos"),
                    rs.getString("seq").length() + rs.getInt("start_pos"),
                    rs.getString("seq"),
                    rs.getInt("reading_frame")
            );
            // Add ORF to ORF prediction
            result.addORF(orf);
        }

        con.close();
        return result;
    }

    /**
     * Gets ORF from database by ORF id
     * @param orfId id of the ORF
     * @return ORF - ORF requested by id
     * @throws ClassNotFoundException when no suitable driver is found
     */
    public static ORF getOrf(String orfId) throws ClassNotFoundException,
            SQLException {

        Connection con = connect();
        assert con != null;

        // Select data for ORF by id
        String query = "select seq, start_pos, reading_frame from " +
                "orf where id = " + orfId + ";";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        // Use data to make new ORF object
        ORF orf = new ORF(
                Integer.parseInt(orfId),
                rs.getInt("start_pos"),
                rs.getInt("start_pos") + rs.getString("seq").length(),
                rs.getString("seq"),
                rs.getInt("reading_frame")
        );

        con.close();
        return orf;
    }

    /**
     * Save blast results of ORF to database
     * @param blastResults
     */
    public static void saveBlastResult(ArrayList<BlastResult> blastResults) {
        // ORF id ophalen door de sequentie (seq van BlastResult)

        // Save blast results of ORF to database

    }

    /**
     * Get summary of all blast searches for one ORF
     * @param ORFid id of the ORF result from which the blast results are
     *              requested
     * @return ArrayList with summary of blast results
     * @throws ClassNotFoundException when no suitable driver is found
     */
    public static ArrayList<ArrayList<String>> getAllBlastSearches(int ORFid) throws ClassNotFoundException, SQLException {
        ArrayList<ArrayList<String>> blastResultSummary =
                new ArrayList<>();

        Connection con = connect();
        assert con != null;

        // Get all blast results
        String query = "select id, `database`, organism, `exclude`, " +
                "max_target_sequences, expect_threshold, word_size " +
                "from blast_search where orf_id = " + ORFid + ";";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            // Add blast query to arrayList one by one
            ArrayList<String> newBlastResult = new ArrayList<>();
            newBlastResult.add(rs.getString("id"));
            newBlastResult.add(rs.getString("database"));
            newBlastResult.add(rs.getString("organism"));
            newBlastResult.add(rs.getString("exclude"));
            newBlastResult.add(rs.getString("max_target_sequences"));
            newBlastResult.add(rs.getString("expect_threshold"));
            newBlastResult.add(rs.getString("word_size"));

            blastResultSummary.add(newBlastResult);
        }
        con.close();

        return blastResultSummary;
    }

    /**
     * Get blast results by BlastSearch id
     * @param blastSearchId id of the blast search from which the results
     *                      are requested
     * @return ArrayList of BlastResult objects from the search
     * @throws ClassNotFoundException when no suitable driver is found
     */
    public static ArrayList<BlastResult> getBlastResult(int blastSearchId) throws ClassNotFoundException {
        ArrayList<BlastResult> blastResults = new ArrayList<>();

        Connection con = connect();
        assert con != null;

        // Get all blast results by blast search id
        String query = "select seq, aligned_seq, e_value, acc_code, identity_percent, title from " +
                "blast_result where blast_search_id = " + blastSearchId + ";";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                BlastResult result = new BlastResult(
                        rs.getString("seq"),
                        rs.getString("aligned_seq"),
                        rs.getDouble("e_value"),
                        rs.getString("acc_code"),
                        rs.getDouble("identity_percent"),
                        rs.getString("title")
                );
                blastResults.add(result);
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blastResults;
    }

    /**
     * Make connection to database containing all data on the ORF predictions
     * @return Connection
     * @throws ClassNotFoundException when no suitable driver is found
     */
    protected static Connection connect() throws ClassNotFoundException {
        // Credentials
        String MySQLURL = "jdbc:mysql://165.232.120.23:3306/";
        String databseUserName = "course7user";
        String databasePassword = "ORFfound!01";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
        try {
            con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
            if (con != null) {
                // Set database
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

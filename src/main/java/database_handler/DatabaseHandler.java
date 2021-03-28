package database_handler;

import blast_handler.BlastResult;
import orf_processing.ORF;
import orf_processing.ORFResult;

import java.sql.*;
import java.util.ArrayList;

//TODO: documentatie toevoegen.
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
    public static ArrayList<ArrayList<String>> getResultSummary(int userId) throws ClassNotFoundException {
        ArrayList<ArrayList<String>> resultSummary =
                new ArrayList<>();

        Connection con = connect();
        assert con != null;

        String query = "select id, name, seq, acc_code, header from " +
                "orf_prediction where user_id = " + userId + ";";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String seq = rs.getString("seq");
                if (seq.length() > 20) {
                    seq = seq.substring(0, 18) + "...";
                }
                ArrayList<String> newResult = new ArrayList<>();
                newResult.add(rs.getString("id"));
                newResult.add(rs.getString("name"));
                newResult.add(seq);
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

    /**
     * get ORF result from database by user id
     * @param resultId
     * @return
     */
    public static ORFResult getResult(int resultId) throws ClassNotFoundException, SQLException {
//        ORFResult result = new ORFResult("", "", 0);  // heb ff lege data
        // ingevuld om error te voorkomen

        Connection con = connect();
        assert con != null;

        String query = "select name, seq, user_id, acc_code, header from " +
                "orf_prediction where id = " + resultId + ";";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        ORFResult result = new ORFResult(
                rs.getString("seq"),
                rs.getString("name"),
                rs.getInt("user_id"),
                rs.getString("acc_code"),
                rs.getString("header")
        );

        query = "select id, seq, start_pos from " +
                "orf where orf_prediction_id = " + resultId + ";";
        Statement stmt2 = con.createStatement();
        rs = stmt2.executeQuery(query);
        while (rs.next()) {
            ORF orf = new ORF(
                rs.getInt("id"),
                rs.getInt("start_pos"),
                rs.getString("seq").length() + rs.getInt("start_pos"),
                rs.getString("seq")
            );
            result.addORF(orf);
        }

        con.close();
        return result;
    }

    //TODO: documentatie toevoegen.
    public static void saveBlastResult(ArrayList<BlastResult> blastResults) {
        // ORF id ophalen door de sequentie (seq van BlastResult)?

        // Save blast results of ORF to database

    }

    /**
     * get summary of all blastresults
     * @param ORFid
     * @return
     */
    //TODO: documentatie aanvullen.
    public static ArrayList<ArrayList<String>> getAllBlastResults(int ORFid) throws ClassNotFoundException, SQLException {
        ArrayList<ArrayList<String>> blastResultSummary =
                new ArrayList<>();

        Connection con = connect();
        assert con != null;

        String query = "select id, `database`, organism, `exclude`, " +
                "max_target_sequences, expect_threshold, word_size " +
                "from blast_search where orf_id = " + ORFid + ";";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
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
     * get blast results by BlastSearch id
     * @param blastSearchId
     * @return
     */
    //TODO: documentatie aanvullen.
    public static ArrayList<BlastResult> getBlastResult(int blastSearchId) throws ClassNotFoundException {
        ArrayList<BlastResult> blastResults = new ArrayList<>();

        //
        Connection con = connect();
        assert con != null;

        String query = "select seq, aligned_seq, e_value, acc_code, identity_percent, title from " +
                "blast_result where blast_search_id = " + blastSearchId + ";";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
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

    //TODO: documentatie toevoegen.
    protected static Connection connect() throws ClassNotFoundException {
        String MySQLURL = "jdbc:mysql://165.232.120.23:3306/";
        String databseUserName = "course7user";
        String databasePassword = "ORFfound!01";

        Class.forName("com.mysql.cj.jdbc.Driver");
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

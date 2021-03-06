package database_handler;

import blast_handler.BlastResult;
import orf_processing.ORFResult;

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
        ORFResult result = new ORFResult();

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
}

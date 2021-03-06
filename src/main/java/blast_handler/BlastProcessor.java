package blast_handler;

import java.util.ArrayList;
import java.util.HashMap;

public class BlastProcessor {
    /**
     * Execute blast with parameters entered by the user
     * @param blastQuery HashMap of parameters for blast
     * @return The filename of the XML file containing the blast result
     */
    public static String blast(HashMap<String, String> blastQuery) {
        String XMLpath = "";

        // Execute blast with parameters in blastQuery. Save name of result file
        // to XMLpath

        return XMLpath;
    }

    public static ArrayList<BlastResult> parseXML(String XMLpath) {
        ArrayList<BlastResult> BlastResults = new ArrayList<BlastResult>();

        // Parse the XML file from blast to BlastResult objects

        return BlastResults;
    }

}

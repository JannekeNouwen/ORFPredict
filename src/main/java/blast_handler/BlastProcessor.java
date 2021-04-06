package blast_handler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Handling of blast searches
 *
 * @author Yuri, Janneke & Max
 * @version 1
 */
public class BlastProcessor {
    /**
     * Execute blast with parameters entered by the user
     *
     * @param blastQuery HashMap of parameters for blast
     * @return The filename of the XML file containing the blast result
     */
    public static String blast(HashMap<String, String> blastQuery) throws Exception {
        try {
            String outputFile = blastQuery.get("output_file");
            String header = outputFile.substring(0, outputFile.length() - 5);


            // Add the sequence in fasta format to a bash variable
            String fastaCommand = "echo \">" + header + "\n" + blastQuery.get(
                    "seq") + "\" > /home/blast_output_ORFPredict/" + header + ".fasta";
            Runtime.getRuntime().exec(new String[]{"bash", "-c", fastaCommand});


            // Make sure the fasta file is created before the blast job is added
            // to the queue.
            File f = new File("/home/blast_output_ORFPredict/" + header + ".fasta");

            while (!f.exists()) {
                TimeUnit.SECONDS.sleep(1);
            }

            // Make a blast query to the queue with the parameters entered by
            // the user
            String blastCommand = blastQuery.get("program") +
                    " -db " + blastQuery.get("database") +
                    " -query /home/blast_output_ORFPredict/" + header + ".fasta" +
                    " -evalue " + blastQuery.get("evalue") +
                    " -word_size " + blastQuery.get("word_size") +
                    " -max_target_seqs " + blastQuery.get("alignment_number") +
                    " -out /home/blast_output_ORFPredict/" + outputFile +
                    " -outfmt 15" +
                    " -remote ";

            // Add the blast query to the queue
            String tsCommand =
                    "ts bash --rcfile ~/.bashrc -c -i '" + blastCommand + "'";
            Runtime.getRuntime().exec(new String[]{"bash", "-c", tsCommand});

            System.out.println(tsCommand);
            // When the blast is executed, rename the file to show it's finished
            String finishedCommand =
                    "mv /home/blast_output_ORFPredict/" + header + ".json " +
                            "/home/blast_output_ORFPredict/" + header +
                            "_finished.json";
            tsCommand =
                    "ts -d " + finishedCommand;
            Runtime.getRuntime().exec(new String[]{"bash", "-c", tsCommand});

            return "/home/blast_output_ORFPredict/" + header + "_finished.json";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Parse the XML file from blast to BlastResult objects
     *
     * @param JSONpath output file from blast
     * @return ArrayList of al blast hits as objects
     */
    public static ArrayList<BlastResult> parseXML(String JSONpath) throws IOException, ParseException {
        ArrayList<BlastResult> BlastResults = new ArrayList<>();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(JSONpath));

        // TODO nu komt er een lege pagina als er geen hits zijn. Zelfde
        //  soort pagina maken als bij de orf prediction waarop staat dat er
        //  geen hits zijn gevonden

        JSONObject jsonObject = (JSONObject) obj;
        JSONArray blastOutput = (JSONArray) jsonObject.get("BlastOutput2");
        for (JSONObject object : (Iterable<JSONObject>) blastOutput) {
            try {
                // Get the blast hits from the nested JSON
                JSONObject blastReport = (JSONObject) object.get("report");
                JSONObject blastResults = (JSONObject) blastReport.get("results");
                JSONObject blastSearch = (JSONObject) blastResults.get(
                        "search");
                JSONArray blastHits = (JSONArray) blastSearch.get("hits");
                // Loop over the blast hits and save the hits as result
                // objects in the BlastResults ArrayList
                for (JSONObject hit : (Iterable<JSONObject>) blastHits) {
                    JSONArray description = (JSONArray) hit.get("description");
                    JSONObject descObject = (JSONObject) description.get(0);
                    JSONArray hsps = (JSONArray) hit.get("hsps");
                    JSONObject hspsObject = (JSONObject) hsps.get(0);
                    float identityPercent = Float.parseFloat(hspsObject.get(
                            "identity").toString()) * 100 / Float.parseFloat(hspsObject.get(
                                    "align_len").toString());
                    BlastResult newResult = new BlastResult (
                            hspsObject.get("qseq").toString(),
                            hspsObject.get("hseq").toString(),
                            hspsObject.get("midline").toString(),
                            Double.parseDouble(hspsObject.get("evalue").toString()),
                            descObject.get("accession").toString(),
                            identityPercent,
                            descObject.get("title").toString()
                    );

                    BlastResults.add(newResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return BlastResults;
    }

}

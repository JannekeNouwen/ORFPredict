package blast_handler;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
        System.out.println("starting blast()");

//        // Check if a queue already exists. If not, create one
//        String command = "screen -list";
//        Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//        String line;
//        boolean queueFound = false;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//            if (line.contains("blastqueue")) {
//                queueFound = true;
//
//            }
//        }
//        if (!queueFound) {
//            command = "screen -d -m -S blastqueue -c ~/.bashrc";
//            Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
//        }
        try {
            String outputFile = blastQuery.get("output_file");
            String header = outputFile.substring(0, outputFile.length() - 5);


            // Add the sequence in fasta format to a bash variable
            String fastaCommand = "echo \">" + header + "\n" + blastQuery.get(
                    "seq") + "\" > /home/blast_output_ORFPredict/" + header + ".fasta";
            Runtime.getRuntime().exec(new String[]{"bash", "-c", fastaCommand});
            System.out.println(fastaCommand);


            // Make sure the fasta file is created before the blast job is added
            // to the queue.

            File f = new File("/home/blast_output_ORFPredict/" + header + ".fasta");
            TimeUnit.SECONDS.sleep(3);

            while (!f.exists()) {
                System.out.println("fasta file not found");
                TimeUnit.SECONDS.sleep(1);
            }

            // Make a blast query to the queue with the parameters entered by
            // the user
            String blastCommand = blastQuery.get("program") +
                    " -db " + blastQuery.get("database") +
//                " -query " + "<(echo -e \">" + header + "\\n" + blastQuery.get(
//                        "seq") + "\")" +
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
            System.out.println(tsCommand);
            Runtime.getRuntime().exec(new String[]{"bash", "-c", tsCommand});

            // TODO remove temp fasta file

            // When the blast is executed, rename the file to show it's finished
            String finishedCommand =
                    "mv /home/blast_output_ORFPredict/" + header + ".json " +
                            "/home/blast_output_ORFPredict/" + header +
                            "_finished.json";
            tsCommand =
                    "ts -d " + finishedCommand;
            System.out.println(tsCommand);
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
        ArrayList<BlastResult> BlastResults = new ArrayList<BlastResult>();

        JSONpath = "/home/blast_output_ORFPredict/1_1550_2021-04-05T22_20_23.593056_finished.json"; //TODO dit weghalen, is voor testen

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(JSONpath));

        // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject jsonObject = (JSONObject) obj;

        // A JSON array. JSONObject supports java.util.List interface.
        JSONArray blastOutput = (JSONArray) jsonObject.get("BlastOutput2");
        for (JSONObject object : (Iterable<JSONObject>) blastOutput) {
            System.out.println(object);
            try {
                JSONObject blastReport = (JSONObject) object.get("report");
                JSONObject blastResults = (JSONObject) blastReport.get("results");
                JSONObject blastSearch = (JSONObject) blastResults.get(
                        "search");
                JSONArray blastHits = (JSONArray) blastSearch.get("hits");
                for (JSONObject hit : (Iterable<JSONObject>) blastHits) {
//                    this.seq = seq;
//                    this.alignedSeq = alignedSeq;
//                    this.eValue = eValue;
//                    this.accCode = accCode;
//                    this.identityPercent = identityPercent;
//                    this.title = title;
                    JSONArray description = (JSONArray) hit.get("description");
                    JSONObject descObject = (JSONObject) description.get(0);
                    JSONArray hsps = (JSONArray) hit.get("hsps");
                    JSONObject hspsObject = (JSONObject) hsps.get(0);
                    float identityPercent = Float.parseFloat(hspsObject.get(
                            "identity").toString()) * 100 / Float.parseFloat(hspsObject.get(
                                    "align_len").toString());
                    BlastResult newResult = new BlastResult (
                            hspsObject.get("qseq").toString(),
                            hspsObject.get("midline").toString(),
                            Double.parseDouble(hspsObject.get("evalue").toString()),
                            descObject.get("accession").toString(),
                            identityPercent,
                            descObject.get("title").toString()
                    );
                    System.out.println(
                            hspsObject.get("qseq").toString() +
                            hspsObject.get("midline").toString() +
                            Double.parseDouble(hspsObject.get("evalue").toString())+
                            descObject.get("accession").toString() +
                            identityPercent +
                            descObject.get("title").toString());
                    System.out.println("\n");
                    BlastResults.add(newResult);
                }
                System.out.println("FOUND RESULTS");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO remove json file

        return BlastResults;
    }

}

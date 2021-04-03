package blast_handler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handling of blast searches
 * @version 1
 * @author Yuri, Janneke & Max
 */
public class BlastProcessor {
    /**
     * Execute blast with parameters entered by the user
     * @param blastQuery HashMap of parameters for blast
     * @return The filename of the XML file containing the blast result
     */
    public static String blast(HashMap<String, String> blastQuery) throws Exception {
        System.out.println("starting blast()");

        // Check of er al een queue bestaat en maak er anders een aan
        Runtime.getRuntime().exec(new String[] { "bash", "-c", "cd" });
        String command = "screen -list";
        Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        boolean queueFound = false;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.contains("blastqueue")) {
                queueFound = true;

            }
        }
        if (!queueFound) {
            command = "screen -d -m -S blastqueue";
            Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
        }


        // Stuur een blast opdracht naar de queue met de parameters die de
        // gebruiker heeft ingevoerd.
        String header = blastQuery.get("output_file").substring(0,
                blastQuery.get("output_file").length()-4);
        String blastCommand = blastQuery.get("program") +
                " -db " + blastQuery.get("database") +
                " -query " + "<(echo -e \\\">" + header + "\\\\n" + blastQuery.get(
                        "seq") + "\\\")" +
                " -evalue " + blastQuery.get("evalue") +
                " -word_size " + blastQuery.get("word_size") +
                " -max_target_seqs " + blastQuery.get("alignment_number") +
                " -out /home/blast_output_ORFPredict/" + blastQuery.get(
                "output_file") +
                " -outfmt 5" +
                " -remote " +
                "^M";

        System.out.println("starting blast with command:");
        System.out.println(blastCommand);

        String screenCommand =
                "screen -S blastqueue -X stuff \"" +blastCommand + "\"";
        System.out.println(screenCommand);

        Runtime.getRuntime().exec(new String[] { "bash", "-c", screenCommand });

        return blastQuery.get("output_file");
    }

    /**
     * Parse the XML file from blast to BlastResult objects
     * @param XMLpath output file from blast
     * @return ArrayList of al blast hits as objects
     */
    public static ArrayList<BlastResult> parseXML(String XMLpath) {
        ArrayList<BlastResult> BlastResults = new ArrayList<BlastResult>();

        // Parse the XML file from blast to BlastResult objects

        return BlastResults;
    }

}

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

        // Check if a queue already exists. If not, create one
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
            command = "screen -d -m -S blastqueue -c ~/.bashrc";
            Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
        }

        String outputFile = blastQuery.get("output_file");
        // Make a blast query to the queue with the parameters entered by
        // the user
        String header = outputFile.substring(0, outputFile.length()-4);
        String blastCommand = blastQuery.get("program") +
                " -db " + blastQuery.get("database") +
                " -query " + "<(echo -e \\\">" + header + "\\\\n" + blastQuery.get(
                        "seq") + "\\\")" +
                " -evalue " + blastQuery.get("evalue") +
                " -word_size " + blastQuery.get("word_size") +
                " -max_target_seqs " + blastQuery.get("alignment_number") +
                " -out /home/blast_output_ORFPredict/" + outputFile +
                " -outfmt 5" +
                " -remote " +
                "^M";

        // Add the blast query to the queue
        String screenCommand =
                "screen -S blastqueue -X stuff \"" +blastCommand + "\"";
        System.out.println(screenCommand);
        Runtime.getRuntime().exec(new String[] { "bash", "-c", screenCommand });

        // When the blast is executed, rename the file to show it's finished
        String finishedCommand =
                "mv /home/blast_output_ORFPredict/" + header + "_finished.xml ^M";
        screenCommand =
                "screen -S blastqueue -X stuff \"" + finishedCommand + "\"";
        System.out.println(screenCommand);

        Runtime.getRuntime().exec(new String[] { "bash", "-c", finishedCommand });

        return "/home/blast_output_ORFPredict/" + header + "_finished.xml";
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

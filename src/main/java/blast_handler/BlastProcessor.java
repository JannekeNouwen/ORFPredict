package blast_handler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.*;

public class BlastProcessor {
    /**
     * Execute blast with parameters entered by the user
     * @param blastQuery HashMap of parameters for blast
     * @return The filename of the XML file containing the blast result
     */
    public static String blast(HashMap<String, String> blastQuery) throws Exception {
        System.out.println("starting blast()");
//        NCBIQBlastService service = new NCBIQBlastService();
//        NCBIQBlastAlignmentProperties alignmentProperties =
//                new NCBIQBlastAlignmentProperties();
//        alignmentProperties.setBlastDatabase(blastQuery.get("database"));
//        alignmentProperties.setBlastExpect(Double.parseDouble(blastQuery.get("evalue")));
//        alignmentProperties.setBlastProgram(BlastProgramEnum.valueOf(blastQuery.get("program")));
//        if (!blastQuery.get("organism").equals("") && blastQuery.get(
//                "exclude").equals("false") ) {
//            alignmentProperties.setAlignmentOption(ENTREZ_QUERY, "txid"
//                    + blastQuery.get("organism") +
//                    "[Organism]");
//        } else if (!blastQuery.get("organism").equals("") && blastQuery.get(
//                "exclude").equals("true") ) {
//            alignmentProperties.setAlignmentOption(ENTREZ_QUERY, "NOT txid"
//                    + blastQuery.get("organism") +
//                    "[Organism]");
//        }
//        alignmentProperties.setBlastWordSize(Integer.parseInt(blastQuery.get("word_size")));
//        NCBIQBlastOutputProperties outputProperties = new NCBIQBlastOutputProperties();
//        outputProperties.setAlignmentNumber(Integer.parseInt(blastQuery.get("alignment_number")));
//
//        String rid = service.sendAlignmentRequest(blastQuery.get("seq"),
//                alignmentProperties);

        // TODO specify a better working directory for running on takkie
//        Runtime.getRuntime().exec("cd /home");
        Runtime.getRuntime().exec(new String[] { "bash", "-c", "cd" });
        String command = "screen -list";
        Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Check of er al een queue bestaat en maak er anders een aan
        String line;
        boolean queueFound = false;
        command = "screen -d -m -S blastqueue";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.contains("blastqueue")) {
                queueFound = true;

            }
        }
        if (!queueFound) {
            Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
        }

//        if (!blastQuery.get("organism").equals("") && blastQuery.get(
//                "exclude").equals("false") ) {
//            alignmentProperties.setAlignmentOption(ENTREZ_QUERY, "txid"
//                    + blastQuery.get("organism") +
//                    "[Organism]");
//        } else if (!blastQuery.get("organism").equals("") && blastQuery.get(
//                "exclude").equals("true") ) {
//            alignmentProperties.setAlignmentOption(ENTREZ_QUERY, "NOT txid"
//                    + blastQuery.get("organism") +
//                    "[Organism]");
//        }


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
//        Runtime.getRuntime().exec("screen -S blastqueue -X stuff \"" + blastCommand +
//                "\"");




//        while (false) {
//            System.out.println("Waiting for results. Sleeping for 5 seconds");
//            Thread.sleep(5000);
//        }

        // read results when they are ready
//        InputStream in = service.getAlignmentResults(rid, outputProperties);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//        // write blast output to specified file
//        File f = new File(blastQuery.get("output_file"));
//        System.out.println("Saving query results in file " + f.getAbsolutePath());
//        FileWriter writer = new FileWriter(f);
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            writer.write(line + System.getProperty("line.separator"));
//        }
//        // clean up
//        IOUtils.close(writer);
//        IOUtils.close(reader);
//
//        // delete given alignment results from blast server (optional operation)
//        service.sendDeleteRequest(rid);

        return blastQuery.get("output_file");
    }

    public static ArrayList<BlastResult> parseXML(String XMLpath) {
        ArrayList<BlastResult> BlastResults = new ArrayList<BlastResult>();

        // Parse the XML file from blast to BlastResult objects

        return BlastResults;
    }

}

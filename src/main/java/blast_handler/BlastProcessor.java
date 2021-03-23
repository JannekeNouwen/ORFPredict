package blast_handler;

import java.util.ArrayList;
import java.util.HashMap;

import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import java.io.*;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.*;

public class BlastProcessor {
    /**
     * Execute blast with parameters entered by the user
     * @param blastQuery HashMap of parameters for blast
     * @return The filename of the XML file containing the blast result
     */
    public static String blast(HashMap<String, String> blastQuery) {
        String XMLpath = "";


        NCBIQBlastService service = new NCBIQBlastService();
        NCBIQBlastAlignmentProperties alignmentProperties =
                new NCBIQBlastAlignmentProperties();
        alignmentProperties.setBlastDatabase(blastQuery.get("database"));
        alignmentProperties.setBlastProgram(BlastProgramEnum.valueOf(blastQuery.get("program")));
        alignmentProperties.setBlastWordSize(Integer.parseInt(blastQuery.get("word_size")));
        NCBIQBlastOutputProperties outputProperties = new NCBIQBlastOutputProperties();
        outputProperties.setAlignmentNumber(Integer.parseInt(blastQuery.get("alignment_number")));
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

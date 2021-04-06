package orf_processing;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * class used to store results from ORF predict query
 * @version 1
 * @author Yuri, Janneke & Max
 */
public class ORFResult {
    private final String seq;
    private final String name;
    private final int userId;
    private String header;
    private final ArrayList<ORF> ORFArray = new ArrayList<>();

    /**
     * Constructor of ORFResult class
     * @param seq String containing original input sequence
     * @param name String containing query name
     * @param userId int containing the id of the user executing the query
     * @param header String containing the header of the input
     */
    public ORFResult(String seq, String name, int userId, String header) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
        this.header = header;
    }

    /**
     * Alternative constructor of ORFResult class without header param
     * @param seq String containing original input sequence
     * @param name String containing query name
     * @param userId int containing the id of the user executing the query
     */
    public ORFResult(String seq, String name, int userId) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
    }

    /**
     * Format the ORF's to be used for visualisation
     * @return ArrayList with an ArrayList for each row of ORFs
     */
    public ArrayList<ArrayList<ORF>> getFormattedORFs() {
        // ArrayList containing the lengths of each row
        ArrayList<Integer> rowLengths = new ArrayList<>();
        rowLengths.add(0, -6);
        int row = 0;
        int numRows = 0;
        boolean placed = false;
        ArrayList<ArrayList<ORF>> formattedORFs = new ArrayList<>();
        formattedORFs.add(row, new ArrayList<>());

        // Sort ORFs by start position
        ORFArray.sort(Comparator.comparingInt(ORF::getStart));

        // Loop over all ORFs and place them in the row where their start
        // position is bigger than the rowlength, so the ORF fits in the row
        for (ORF orf : ORFArray) {
            placed = false;
            numRows = rowLengths.size();
            for (Integer length : rowLengths) {
                if (row > numRows) {
                    // Add orf to new row
                    formattedORFs.add(row, new ArrayList<>());
                    formattedORFs.get(row).add(orf);
                    rowLengths.add(row, orf.getStop());
                    placed = true;
                    break;
                }
                if (length + 5 < orf.getStart()) {
                    // ORF fits in the row
                    formattedORFs.get(row).add(orf);
                    rowLengths.set(row, orf.getStop());
                    placed = true;
                    break;
                }
                row++;
            }
            if (!placed) {
                // Add orf to new row
                formattedORFs.add(row, new ArrayList<>());
                formattedORFs.get(row).add(orf);
                rowLengths.add(row, orf.getStop());
            }
            row = 0;
        }
        return formattedORFs;
    }

    /**
     * Add ORF to ORFArray of current ORFResult obj
     * @param orf ORF object containing a predicted ORF
     */
    public void addORF(ORF orf) {
        ORFArray.add(orf);
    }

    /**
     * Get original input sequence
     * @return String containing original input sequence
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Get name of query
     * @return String containing the name of the query
     */
    public String getName() {
        return name;
    }

    /**
     * Get userId of user that executed this prediction
     * @return int containing the id of the user that executed this prediction
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Get header of query input
     * @return String containg the header that was included in the input
     */
    public String getHeader() {
        return header;
    }

    /**
     * Get the List of arrays stored in this ORFResult obj
     * @return ArrayList<ORF> containing all ORF's in this object
     */
    public ArrayList<ORF> getORFs() {
        return ORFArray;
    }

}

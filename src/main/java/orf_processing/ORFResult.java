package orf_processing;

import java.util.ArrayList;
import java.util.Comparator;

public class ORFResult {
    private final String seq;
    private final String name;
    private final int userId;
    private String header;
    private final ArrayList<ORF> ORFArray = new ArrayList<>();

    public ORFResult(String seq, String name, int userId, String header) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
        this.header = header;
    }

    public ORFResult(String seq, String name, int userId) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
    }

    public ArrayList<ArrayList<ORF>> getFormattedORFs() {
        ArrayList<Integer> rowLengths = new ArrayList<>();
        rowLengths.add(0, -6);
        int row = 0;
        int numRows = 0;
        boolean placed = false;
        ArrayList<ArrayList<ORF>> formattedORFs = new ArrayList<>();
        formattedORFs.add(row, new ArrayList<>());
        System.out.println("Number of ORF's: " + ORFArray.size());

        ORFArray.sort(Comparator.comparingInt(ORF::getStart));

        for (ORF orf : ORFArray) {
            System.out.println(orf.getStart());
            placed = false;
            numRows = rowLengths.size();
            for (Integer length : rowLengths) {
                if (row > numRows) {
                    formattedORFs.add(row, new ArrayList<>());
                    formattedORFs.get(row).add(orf);
                    rowLengths.add(row, orf.getStop());
                    placed = true;
                    break;
                }
                if (length + 5 < orf.getStart()) {
                    formattedORFs.get(row).add(orf);
                    rowLengths.set(row, orf.getStop());
                    placed = true;
                    break;
                }
                row++;
            }
            if (!placed) {
                formattedORFs.add(row, new ArrayList<>());
                formattedORFs.get(row).add(orf);
                rowLengths.add(row, orf.getStop());
            }
            row = 0;
        }
        System.out.println("Formatted the ORFs to " + rowLengths.size() +
                "rows.");
        return formattedORFs;
    }

    public void addORF(ORF orf) {
        ORFArray.add(orf);
    }

    public String getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<ORF> getORFs() {
        return ORFArray;
    }

}

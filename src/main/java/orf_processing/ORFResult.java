package orf_processing;

import java.util.ArrayList;
import java.util.Comparator;

//TODO: documentatie toevoegen.
public class ORFResult {
    private final String seq;
    private final String name;
    private final int userId;
    private String accCode;
    private String header;
    private final ArrayList<ORF> ORFArray = new ArrayList<>();

    //TODO: documentatie toevoegen.
    public ORFResult(String seq, String name, int userId, String accCode) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
        this.accCode = accCode;
    }

    //TODO: documentatie toevoegen.
    public ORFResult(String seq, String name, int userId, String accCode, String header) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
        this.accCode = accCode;
        this.header = header;
    }

    //TODO: documentatie toevoegen.
    public ORFResult(String seq, String name, int userId) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
    }

    //TODO: documentatie toevoegen.
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

    //TODO: documentatie toevoegen.
    public void addORF(ORF orf) {
        ORFArray.add(orf);
    }

    //TODO: documentatie toevoegen.
    public String getSeq() {
        return seq;
    }

    //TODO: documentatie toevoegen.
    public String getName() {
        return name;
    }

    //TODO: documentatie toevoegen.
    public int getUserId() {
        return userId;
    }

    //TODO: documentatie toevoegen.
    public String getAccCode() {
        return accCode;
    }

    //TODO: documentatie toevoegen.
    public String getHeader() {
        return header;
    }

    //TODO: documentatie toevoegen.
    public ArrayList<ORF> getORFs() {
        return ORFArray;
    }

}

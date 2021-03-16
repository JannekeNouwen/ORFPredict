package orf_processing;

import java.util.ArrayList;

public class ORFResult {
    private String seq;
    private String name;
    private int userId;
    private String accCode;
    private String header;
    private ArrayList<ORF> ORFArray;

    public ORFResult(String seq, String name, int userId, String accCode) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
        this.accCode = accCode;
    }

    public ORFResult(String seq, String name, int userId, String accCode, String header) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
        this.accCode = accCode;
        this.header = header;
    }

    public ORFResult(String seq, String name, int userId) {
        this.seq = seq;
        this.name = name;
        this.userId = userId;
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

    public String getAccCode() {
        return accCode;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<ORF> getORFs() {
        return ORFArray;
    }
}

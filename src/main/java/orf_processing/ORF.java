package orf_processing;

public class ORF {
    private final int start;
    private final int stop;
    private final String seq;

    public ORF(int start, int stop, String seq) {
        this.start = start;
        this.stop = stop;
        this.seq = seq;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    public String getSeq() {
        return seq;
    }
}

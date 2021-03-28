package orf_processing;

//TODO: documentatie toevoegen.
public class ORF implements Comparable<ORF> {
    private final int id;
    private final int start;
    private final int stop;
    private final String seq;

    //TODO: documentatie toevoegen.
    public ORF(int id, int start, int stop, String seq) {
        this.id = id;
        this.start = start;
        this.stop = stop;
        this.seq = seq;
    }

    //TODO: documentatie toevoegen.
    public int getId() {
        return id;
    }

    //TODO: documentatie toevoegen.
    public int getStart() {
        return start;
    }

    //TODO: documentatie toevoegen.
    public int getStop() {
        return stop;
    }

    //TODO: documentatie toevoegen.
    public String getSeq() {
        return seq;
    }

    //TODO: documentatie toevoegen.
    @Override
    public int compareTo(ORF o) {
        return Integer.compare(this.getStart(), o.getStart());
    }
}

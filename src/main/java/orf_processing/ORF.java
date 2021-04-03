package orf_processing;

/**
 * Class used to store predicted ORF's
 * @version 1
 * @author Yuri, Janneke & Max
 */
public class ORF implements Comparable<ORF> {
    private final int id;
    private final int start;
    private final int stop;
    private final String seq;
    private final int readingFrame;

    /**
     * Constructor of an ORF object
     * @param id int containing the id of this ORF
     * @param start int containing the index of start codon according to original seq
     * @param stop int containing the index of stop codon according to original seq
     * @param seq String containing the sequence of the ORF
     * @param readingFrame int containg a number between 1-6 depicting the reading frame the ORF was found in (1-3 reading frames, 4-6 reverse reading frames)
     */
    public ORF(int id, int start, int stop, String seq, int readingFrame) {
        this.id = id;
        this.start = start;
        this.stop = stop;
        this.seq = seq;
        this.readingFrame = readingFrame;
    }

    /**
     * Get the id of the ORF
     * @return int containing id of ORF
     */
    public int getId() {
        return id;
    }

    /**
     * get the start index of ORF
     * @return int containing start index of ORF
     */
    public int getStart() {
        return start;
    }

    /**
     * Get the stop index of ORF
     * @return int containing stop index of ORF
     */
    public int getStop() {
        return stop;
    }

    /**
     * Get the sequence of the ORF
     * @return String containing the sequence of the ORF
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Get the reading frame of the ORF
     * @return int containing a number between 1 and 6 depicting the reading frame of the ORF
     */
    public int getReadingFrame() {
        return readingFrame;
    }

    /**
     * Compare the start indexes of this ORF with another
     * @param o ORF object to compare against original
     * @return int containing the value 0 if x == y; a value less than 0 if x < y; and a value greater than 0 if x > y
     */
    @Override
    public int compareTo(ORF o) {
        return Integer.compare(this.getStart(), o.getStart());
    }
}

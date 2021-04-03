package blast_handler;

/**
 * Object containing information on a single BLAST hit
 * @version 1
 * @author Yuri, Janneke & Max
 *
 * seq - sequence of the blast hit
 * alignedSeq - sequence with gaps
 * eValue - e-value of the blast hit
 * accCode - accession code of the sequence
 * identityPercent - identity percent of the blast hit
 * title - title of the blast hit
 */
public class BlastResult {

    private final String seq;
    private final String alignedSeq;
    private final double eValue;
    private final String accCode;
    private final double identityPercent;
    private final String title;

    /**
     * Constructor for BlastResult
     */
    public BlastResult(
            String seq,
            String alignedSeq,
            double eValue,
            String accCode,
            double identityPercent,
            String title
    ) {
        this.seq = seq;
        this.alignedSeq = alignedSeq;
        this.eValue = eValue;
        this.accCode = accCode;
        this.identityPercent = identityPercent;
        this.title = title;
    }

    /**
     * @return seq - sequence of the blast hit
     */
    public String getSeq() {
        return seq;
    }

    /**
     * @return alignedSeq - sequence with gaps
     */
    public String getAlignedSeq() {
        return alignedSeq;
    }

    /**
     * @return eValue - e-value of the blast hit
     */
    public double geteValue() {
        return eValue;
    }

    /**
     * @return accCode - accession code of the sequence
     */
    public String getAccCode() {
        return accCode;
    }

    /**
     * @return identityPercent - identity percent of the blast hit
     */
    public double getIdentityPercent() {
        return identityPercent;
    }

    /**
     * @return title - title of the blast hit
     */
    public String getTitle() {
        return title;
    }
}

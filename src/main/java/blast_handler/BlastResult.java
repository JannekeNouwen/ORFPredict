package blast_handler;

public class BlastResult {
    private final String seq;
    private final String alignedSeq;
    private final double eValue;
    private final String accCode;
    private final double identityPercent;
    private final String title;

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

    public String getSeq() {
        return seq;
    }

    public String getAlignedSeq() {
        return alignedSeq;
    }

    public double geteValue() {
        return eValue;
    }

    public String getAccCode() {
        return accCode;
    }

    public double getIdentityPercent() {
        return identityPercent;
    }

    public String getTitle() {
        return title;
    }
}

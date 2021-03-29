package blast_handler;

//TODO: documentatie toevoegen.
public class BlastResult {
    private final String seq;
    private final String alignedSeq;
    private final double eValue;
    private final String accCode;
    private final double identityPercent;
    private final String title;

    //TODO: documentatie toevoegen.
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

    //TODO: documentatie toevoegen.
    public String getSeq() {
        return seq;
    }

    //TODO: documentatie toevoegen.
    public String getAlignedSeq() {
        return alignedSeq;
    }

    //TODO: documentatie toevoegen.
    public double geteValue() {
        return eValue;
    }

    //TODO: documentatie toevoegen.
    public String getAccCode() {
        return accCode;
    }

    //TODO: documentatie toevoegen.
    public double getIdentityPercent() {
        return identityPercent;
    }

    //TODO: documentatie toevoegen.
    public String getTitle() {
        return title;
    }
}

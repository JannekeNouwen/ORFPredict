package orf_processing;

import java.io.File;

public class Prediction {
    private String seq;
    private String acc;
    private String header;
    private File fastaFromUser;

    public Prediction(String acc) {
        this.acc = acc;
        this.seq = getSeqByAcc();
    }

    public Prediction(String seq, String accOrHeader) {
        this.seq = seq;
        // if accOrHeader = acc:
            this.acc = accOrHeader;
        // else
            this.header = accOrHeader;
    }

    public Prediction(File fastaFromUser) {
        // get header and seq from user
    }

    private String getSeqByAcc() {
        return "";
    }

    private String getSeqByFile() {
        return "";
    }

    public ORFResult predictSeq() {
        ORFResult result = new ORFResult("", "", 0);

        // predict ORFs

        return result;
    }
}

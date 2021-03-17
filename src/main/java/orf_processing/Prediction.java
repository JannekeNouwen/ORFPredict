package orf_processing;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prediction {
    private String seq;
    private final String input;
    private String type;
    private String header;

    public Prediction(String rawInput) {
        this.input = rawInput;
        typeCheck();
        switch (this.type) {
            case "fasta":
                fastaExtract();
                break;
            case "acccode":
                getSeqByAcc();
                break;
            case "fastanoheader":
                this.seq = input;
                break;
            default:
                seq = null;
                break;
        }
    }

    private void getSeqByAcc() {

    }

    public void fastaExtract() {
        String[] fastaInput = input.split("\\r?\\n");
        this.header = fastaInput[0];
        String[] slice = Arrays.copyOfRange(fastaInput, 1, fastaInput.length);
        this.seq = String.join("", slice);
    }

    public void typeCheck() {
        boolean fastaHeader = input.startsWith(">");
        Pattern nuclPattern = Pattern.compile("([^ACGTURYKMSWBDHVN-]|[^acgturykmswbdhvn-])");
        Matcher nuclMatcher = nuclPattern.matcher(input);
        Pattern accPattern = Pattern.compile("([a-zA-Z]_?[0-9]{5}|[a-zA-Z]{2}_?([0-9]{6}|[0-9]{8}))(\\.[0-9])?");
        Matcher accMatcher = accPattern.matcher(input);
        boolean isNotNucl = nuclMatcher.find();
        boolean isAccCode = accMatcher.find();
        if (isNotNucl) {
            this.type = "invalid";
        } else if (fastaHeader){
            this.type = "fasta";
        } else if (isAccCode) {
            this.type = "acccode";
        } else {
            this.type = "fastanoheader";
        }
    }

    public ORFResult predictSeq() {
        ORFResult result = new ORFResult("", "", 0);

        // predict ORFs

        return result;
    }

    public String getSeq() {
        return seq;
    }

    public String getType() {
        return type;
    }

    public String getHeader() {
        return header;
    }
}

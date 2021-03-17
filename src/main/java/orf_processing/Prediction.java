package orf_processing;

import java.util.Arrays;
import java.util.Locale;
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
                this.seq = input.strip();
                this.seq = this.seq.toUpperCase(Locale.ROOT);
                break;
            default:
                this.seq = null;
                break;
        }

        Pattern nuclPattern = Pattern.compile("[^ACGTURYKMSWBDHVN\\-\\s]");
        Matcher nuclMatcher = nuclPattern.matcher(this.seq);
        if (nuclMatcher.find()) {
            this.type = "invalid";
        }
    }

    private void getSeqByAcc() {
//      TODO: Build logic to retrieve correct sequence with accessioncode from BLAST
    }

    public void fastaExtract() {
        String[] fastaInput = input.split("\\r?\\n");
        this.header = fastaInput[0];
        String[] slice = Arrays.copyOfRange(fastaInput, 1, fastaInput.length);
        this.seq = String.join("", slice);
        this.seq = seq.toUpperCase(Locale.ROOT);
    }

    public void typeCheck() {
        Pattern headerPattern = Pattern.compile("(>.+[\\n\\r])");
        Matcher headerMatcher = headerPattern.matcher(input);
        boolean fastaHeader = headerMatcher.find();
        Pattern accPattern = Pattern.compile("([a-zA-Z]_?[0-9]{5}|[a-zA-Z]{2}_?([0-9]{6}|[0-9]{8}))(\\.[0-9])?");
        Matcher accMatcher = accPattern.matcher(input);
        boolean isAccCode = accMatcher.find();
        if (fastaHeader){
            this.type = "fasta";
        } else if (isAccCode) {
            this.type = "acccode";
        } else {
            this.type = "fastanoheader";
        }
    }

    public ORFResult predictSeq() {
        ORFResult result = new ORFResult("", "", 0);
        String reverseComp = translating.revComp(seq);
        String[] readingFrame1 = seq.split("(?<=\\G...)");
        String[] readingFrame2 = seq.substring(1).split("(?<=\\G...)");
        String[] readingFrame3 = seq.substring(2).split("(?<=\\G...)");
        String[] reverseReadingFrame1 = reverseComp.split("(?<=\\G...)");
        String[] reverseReadingFrame2 = reverseComp.substring(1).split("(?<=\\G...)");
        String[] reverseReadingFrame3 = reverseComp.substring(2).split("(?<=\\G...)");


        //System.out.println(java.util.Arrays.toString(seq.split("(?<=\\G...)")));
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

package orf_processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prediction {
    private String seq;
    private final String input;
    private String type;
    private String header;
    private final int minSize;
    private final String startCodon;

    public Prediction(String rawInput, int minSize, String startCodon) {
        this.input = rawInput;
        this.minSize = minSize;
        this.startCodon = startCodon;
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
        ArrayList<String> readingFrame1 = new ArrayList<>(Arrays.asList(seq.split("(?<=\\G...)")));
        ArrayList<String> readingFrame2 = new ArrayList<>(Arrays.asList(seq.substring(1).split("(?<=\\G...)")));
        ArrayList<String> readingFrame3 = new ArrayList<>(Arrays.asList(seq.substring(2).split("(?<=\\G...)")));
        ArrayList<String> reverseReadingFrame1 = new ArrayList<>(Arrays.asList(reverseComp.split("(?<=\\G...)")));
        ArrayList<String> reverseReadingFrame2 = new ArrayList<>(Arrays.asList(reverseComp.substring(1).split("(?<=\\G...)")));
        ArrayList<String> reverseReadingFrame3 = new ArrayList<>(Arrays.asList(reverseComp.substring(2).split("(?<=\\G...)")));
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        arr.add(readingFrame1);
        arr.add(readingFrame2);
        arr.add(readingFrame3);
        arr.add(reverseReadingFrame1);
        arr.add(reverseReadingFrame2);
        arr.add(reverseReadingFrame3);

        String[] start;
        if (startCodon.equals("atgonly")) {
            start = new String[]{"ATG"};
        } else {
            start = new String[]{"ATG", "CTG", "GTG", "TTG"};
        }

        boolean end = false;
        ArrayList<String> currList;
        for (ArrayList<String> list: arr) {
            for (int index = 0; index <= list.size(); index++) {
                // Loop over list, check for every codon if it is in the "start" array
            }
        }

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

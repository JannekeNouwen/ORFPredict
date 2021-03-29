package orf_processing;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prediction {
    private String seq;
    private final String input;
    private String type;
    private String header;
    private final int minSize;
    private final String startCodon;
    private final String stopCodon;
    private final int userId;
    private String accCode;
    private final String name;

    public Prediction(String rawInput, int minSize, String startCodon, String stopCodon, int userId, String name) {
        this.input = rawInput;
        this.minSize = minSize;
        this.startCodon = startCodon;
        this.stopCodon = stopCodon;
        this.userId = userId;
        this.name = name;
        typeCheck();
        switch (this.type) {
            case "fasta":
                fastaExtract();
                accCode = null;
                break;
            case "acccode":
                getSeqByAcc();
                header = null;
                break;
            case "fastanoheader":
                this.seq = input.strip();
                this.seq = this.seq.toUpperCase(Locale.ROOT);
                header = null;
                accCode = null;
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
        accCode = null;
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

        ArrayList<String> start = new ArrayList<>();
        if (startCodon.equals("atgonly")) {
            start.add("ATG");
        } else {
            start.add("ATG");
            start.add("CTG");
            start.add("GTG");
            start.add("TTG");
        }

        ArrayList<String> stop = new ArrayList<>();
        if (stopCodon.equals("normalstop")) {
            stop.add("TAG");
            stop.add("TAA");
            stop.add("TGA");
        } else {
            stop.add("TAG");
            stop.add("TAA");
            stop.add("TGA");
            stop.add("AGA");
            stop.add("AGG");
            stop.add("TCA");
            stop.add("TTA");
        }

        // Create ORFResult object
        ORFResult result;
        if (accCode == null && header == null) {
            result = new ORFResult(seq, name, userId);
        } else result = new ORFResult(seq, name, userId, Objects.requireNonNullElseGet(accCode, () -> header));

        // Search for all start and stop codon's per frame and combine start and stop codons for ORF's
        int currFrame = 1;
        int orfCount = 1;
        for (ArrayList<String> currList : arr) {
            // Use TreeMap to have found start and stop codons be sorted by index
            Map<Integer, String> startCodons = new TreeMap<>();
            Map<Integer, String> stopCodons = new TreeMap<>();
            int lowestStartIndex = -1;
            for (int index = 0; index < currList.size(); index++) {
                if (start.contains(currList.get(index))) {
                    // Current codon is startcodon, add to startCodons map
                    startCodons.put(index, currList.get(index));
                    if (index < lowestStartIndex | lowestStartIndex == -1) {
                        lowestStartIndex = index;
                    }
                } else if (stop.contains(currList.get(index))) {
                    // Current codon is stopcodon, add to stopCodons map
                    stopCodons.put(index, currList.get(index));
                }
            }

            int currStartIndex;
            int currStopIndex;
            String orfSeq;
            for (Map.Entry<Integer, String> startEntry : startCodons.entrySet()) {
                currStartIndex = startEntry.getKey();
                for (Map.Entry<Integer, String> stopEntry : stopCodons.entrySet()) {
                    currStopIndex = stopEntry.getKey();
                    if ((currStopIndex - currStartIndex) >= minSize) {
                        List<String> currORFList = new ArrayList<>(currList.subList(currStartIndex, currStopIndex));
                        orfSeq = String.join("", currORFList);
                        ORF orf = new ORF(orfCount, currStartIndex * 3, currStopIndex * 3, orfSeq, currFrame);
                        result.addORF(orf);
                        orfCount++;
                        break;
                    }
                }
            }
            currFrame++;
        }

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

package orf_processing;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: documentatie toevoegen.
public class Prediction {
    private String seq;
    private final String input;
    private String type;
    private String header;
    private final int minSize;
    private final String startCodon;
    private final String stopCodon;

    //TODO: documentatie toevoegen.
    public Prediction(String rawInput, int minSize, String startCodon, String stopCodon) {
        this.input = rawInput;
        this.minSize = minSize;
        this.startCodon = startCodon;
        this.stopCodon = stopCodon;
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

    //TODO: documentatie toevoegen.
    private void getSeqByAcc() {
//      TODO: Build logic to retrieve correct sequence with accessioncode from BLAST
    }

    //TODO: documentatie toevoegen.
    public void fastaExtract() {
        String[] fastaInput = input.split("\\r?\\n");
        this.header = fastaInput[0];
        String[] slice = Arrays.copyOfRange(fastaInput, 1, fastaInput.length);
        this.seq = String.join("", slice);
        this.seq = seq.toUpperCase(Locale.ROOT);
    }

    //TODO: documentatie toevoegen.
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

    //TODO: documentatie toevoegen.
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

        // Search for all start and stop codon's per frame and combine start and stop codons for ORF's
        int count = 1;
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

            for (int y = 0; y < stopCodons.size(); y++) {

            }

            int currIndex;
            String currStart;
            String currStop;
            for (Map.Entry<Integer, String> startEntry : startCodons.entrySet()) {
                currIndex = startEntry.getKey();
                currStart = startEntry.getValue();
                for (Map.Entry<Integer, String> stopEntry : stopCodons.entrySet()) {
//                    if (stopEntry.getKey() >)
                }
            }
            System.out.println(count);
            System.out.println(startCodons);
            System.out.println(stopCodons);
            System.out.println("\n");
            count++;
        }

//        boolean end = false;
//        ArrayList<String> currList;
//        List<String> slice;
//        int lowStopIndex = -1;
//        for (ArrayList<String> list: arr) {
//            for (int index = 0; index < list.size(); index++) {
//                // Loop over list, check for every codon if it is in the "start" array
//                if (start.contains(list.get(index))) {
//                    // if current codon is startcodon
//                    slice = list.subList(index, list.size());
//                    for (String stopCodon : stop) {
//                        int stopIndex = slice.indexOf(stopCodon);
//                        if (stopIndex != -1) {
//                            if (stopIndex < lowStopIndex | lowStopIndex == -1) {
//                                lowStopIndex = stopIndex;
//                            }
//                        }
//                    }
//                }
//            }
//        }

        //System.out.println(java.util.Arrays.toString(seq.split("(?<=\\G...)")));
        // predict ORFs

        return result;
    }

    //TODO: documentatie toevoegen.
    public String getSeq() {
        return seq;
    }

    //TODO: documentatie toevoegen.
    public String getType() {
        return type;
    }

    //TODO: documentatie toevoegen.
    public String getHeader() {
        return header;
    }
}

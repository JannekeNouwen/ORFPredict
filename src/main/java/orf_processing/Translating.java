package orf_processing;

import java.util.HashMap;

/**
 * Utility class used to get the reverse complement of a given sequence
 * @version 1
 * @author Yuri, Janneke & Max
 */
public class Translating {

    /**
     * Translate the current sequence into reverse complement
     * @param input String containing the sequence to be translated
     * @return Return String containing the reverse complement of input
     */
    public static String revComp(String input) {
        HashMap<Character, Character> complementMap = new HashMap<>();
        complementMap.put('A', 'T');
        complementMap.put('T', 'A');
        complementMap.put('G', 'C');
        complementMap.put('C', 'G');

        // Loop over string, for every char, get reverse complement nucleotide using complementMap
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            stringBuilder.append(complementMap.get(c));
        }
        return stringBuilder.toString();
    }
}

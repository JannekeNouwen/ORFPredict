package orf_processing;

import java.util.HashMap;

//TODO: documentatie toevoegen.
public class translating {

    //TODO: documentatie toevoegen.
    public static String revComp(String input) {
        HashMap<Character, Character> complementMap = new HashMap<>();
        complementMap.put('A', 'T');
        complementMap.put('T', 'A');
        complementMap.put('G', 'C');
        complementMap.put('C', 'G');

        StringBuilder stringBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            stringBuilder.append(complementMap.get(c));
        }
        return stringBuilder.toString();
    }
}

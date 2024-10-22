package algorithms;

import java.util.Arrays;

public class StringPermutationAlgorithms {

    //verify if the characters of a String are unique
    private boolean isUniqueChars(final String str) {
        if (str.length() > 128) {//there are only 128 ASCII chars
            return false;
        }
        boolean[] booleanArray = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int key = str.charAt(i);
            if (booleanArray[key]) {
                return false;
            }
            booleanArray[key] = true;
        }
        return true;
    }

    //verify if one String is permutation of the other
    private boolean isPermutation(final String a, final String b) {
        if (a.length() != b.length()) {
            return false;
        }
        return sort(a).equals(sort(b));
    }

    private String sort(final String s) {
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    private boolean isPermutationOfPalindrome(final String str) {
        int[] table = buildCharFrequencyTable(str);
        return checkMaxOneOdd(table);
    }

    //check that no more than one character has na odd count
    private boolean checkMaxOneOdd(int[] table) {
        boolean foundOdd = false;
        for (int count : table) {
            if (count % 2 == 1) {
                if (foundOdd) {
                    return false;
                }
                foundOdd = true;
            }
        }
        return true;
    }

    // counts how many times each character appears
    private int[] buildCharFrequencyTable(final String phrase) {
        int[] table = new int[Character.getNumericValue('z') -
                Character.getNumericValue('a') + 1];
        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            if (x != -1) {
                table[x]++;
            }
        }
        return table;
    }

    //map each char to a number
    int getCharNumber(final Character c) {
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');
        int val = Character.getNumericValue(c);
        if (a <= val && val <= z) {
            return val - a;
        }
        return -1;
    }

    public static void main(String[] args) {
        StringPermutationAlgorithms string = new StringPermutationAlgorithms();
        System.out.println("String [abcde] is unique chars = " +
                string.isUniqueChars("abcde"));
        System.out.println("String [abcdee] is unique chars = " +
                string.isUniqueChars("abcdee"));

        System.out.println("isPermutation = " +
                string.isPermutation("gods", "sdog"));
        System.out.println("isPermutation = " +
                string.isPermutation("god", "sdo"));

        System.out.println("isPermutationOfPalindrome = " +
                string.isPermutationOfPalindrome("tact coa"));
        System.out.println("isPermutationOfPalindrome = " +
                string.isPermutationOfPalindrome("ttact coa"));
    }


}

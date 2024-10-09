package algorithms;

import java.util.Arrays;

public class StringAlgorithms {

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
    private boolean isPermutation(final String a, final String b){
        if(a.length() != b.length()){
            return false;
        }
        return sort(a).equals(sort(b));
    }
    private String sort(final String s){
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    public static void main(String[] args) {
        StringAlgorithms string = new StringAlgorithms();
        System.out.println("String [abcde] is unique chars = " +
                string.isUniqueChars("abcde"));
        System.out.println("String [abcdee] is unique chars = " +
                string.isUniqueChars("abcdee"));

        System.out.println("isPermutation = " +
                string.isPermutation("gods","sdog"));
        System.out.println("isPermutation = " +
                string.isPermutation("god","sdo"));
    }


}

package algorithms;

import java.util.*;

public class ComparingStringAlgorithms {

    private boolean isOneStepAway(String a, String b) {
        if (a.equals(b)) {
            return true;
        }
        if (a.length() == b.length() + 1) {
            return isOneInsertionAway(a, b);
        }
        if (a.length() == b.length() - 1) {
            return isOneRemovalAway(a, b);
        }
        if (a.length() == b.length()) {
            return isOneReplacementAway(a, b);
        }
        return false;
    }

    private boolean isOneInsertionAway(String a, String b) {
        List<String> lA = Arrays.asList(a.split(""));
        List<String> lB = new ArrayList<>(Arrays.asList(b.split("")));
        for (int i = 0; i < lA.size(); i++) {
            if (!lA.get(i).equals(lB.get(i))) {
                lB.add(i, lA.get(i));
                return lA.toString().equals(lB.toString());
            }
        }

        return false;
    }

    private boolean isOneRemovalAway(String a, String b) {
        List<String> lA = Arrays.asList(a.split(""));
        List<String> lB = new ArrayList<>(Arrays.asList(b.split("")));
        for (int i = 0; i < lB.size(); i++) {
            if (i >= a.length() || !lA.get(i).equals(lB.get(i))) {//end of A
                lB.remove(i);
                return lA.toString().equals(lB.toString());
            }
        }
        return false;
    }

    private boolean isOneReplacementAway(String a, String b) {
        List<String> lA = Arrays.asList(a.split(""));
        List<String> lB = Arrays.asList(b.split(""));
        for (int i = 0; i < lA.size(); i++) {
            if (!lA.get(i).equals(lB.get(i))) {
                lB.set(i, lA.get(i));
                return lA.toString().equals(lB.toString());
            }
        }

        return false;
    }

    /**
     * Given 2 Strings, write a function to check if
     * they are one edit (or zero edits away)
     */
    public static void main(String[] args) {
        ComparingStringAlgorithms cA = new ComparingStringAlgorithms();
        System.out.println("isOneStepAway = " +
                cA.isOneStepAway("pale", "ple"));
        System.out.println("isOneStepAway = " +
                cA.isOneStepAway("pale", "pales"));
        System.out.println("isOneStepAway = " +
                cA.isOneStepAway("pale", "bale"));
        System.out.println("isOneStepAway = " +
                cA.isOneStepAway("pale", "bae"));
    }
}

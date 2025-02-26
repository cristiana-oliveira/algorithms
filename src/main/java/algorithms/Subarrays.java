package algorithms;

import java.util.List;

public class Subarrays {

    /**
     * Formula:
     * Total sum=∑(array[i]×(i+1)×(N−i))
     * Where:
     * array[i] is the element at index i
     * (i + 1) represents how many subarrays start with array[i]
     * (N - i) represents how many subarrays end with array[i]
     * N is the length of the array
     */
    public static int subArraySum(List<Integer> array) {
        int n = array.size();
        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            totalSum += array.get(i) * (i + 1) * (n - i);
        }
        return totalSum;
    }

    public static void main(String[] args) {

        System.out.println(subArraySum(List.of(4, 5, 6)));
    }
}

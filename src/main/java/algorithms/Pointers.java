package algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pointers {
    /**
     * Input: double array representing duration of movies e.g. durations[] ={1.01, 2.4, 1.01, 1.01, 1.4}. You can watch maximum 3.00 duration movie per day.
     * Find the least number of days needed to finish watching all the movies.
     * Constraint: 1.01 <= duration[i] <= 3.00.
     * (You can choose to watch any movie on a day and won't repeat watching a movie)
     * <p>
     * Sample Test Cases:
     * Input: duration[] = {1.01, 2.4, 1.01, 1.01, 1.4} Output: 3
     * Input: duration[] = {1.01, 2.4, 1.4, 1.6, 2.6, 1.7} Output: 4
     * Input: duration[] = {1.01, 2.4, 1.5, 1.6, 2.6, 1.7} Output: 5
     */
    public static void duration() {
        Double[] duration = {1.01, 2.4, 1.01, 1.01, 1.4};
        List<Double> list = Arrays.asList(duration);
        Collections.sort(list);
        int left = 0;
        int right = list.size() - 1;
        int days = 0;
        for (int i = 0; i < list.size() && right >= left; i++) {
            days++;
            if (list.get(left) + list.get(right) <= 3.0) {
                left++;
                right--;
            } else {
                if (list.get(left) > list.get(right)) {
                    left++;
                } else {
                    right--;
                }
            }

        }
        System.out.println(days);
    }

    public static void main(String[] args) {
        //duration();
        //System.out.println(minStartValue(new int[] {1, -2, -3}));
        System.out.println(Arrays.toString(getAverages(new int[] {7, 4, 3, 9, 1, 8, 5, 2, 6}, 3)));
    }

    /**
     * Given an array of integers nums, you start with an initial positive value startValue.
     * <p>
     * In each iteration, you calculate the step by step sum of startValue plus elements in nums (from left to right).
     * <p>
     * Return the minimum positive value of startValue such that the step by step sum is never less than 1.
     */
    public static int minStartValue(int[] nums) {
        int startValue = 1;
        int left = 0;
        int result = 1;
        while (left < nums.length) {
            int sum = result + nums[left];
            if (sum >= 1) {
                left++;
                result = sum;
            } else {
                startValue++;
                result = startValue;
                left = 0;
            }
            if (left == nums.length) {
                return startValue;
            }
        }
        return startValue;
    }

    public static int[] getAverages(int[] nums, int k) {
        int middle = k;
        int[] result = new int[nums.length];
        int left = middle - k;
        int right = middle + k;
        for (int i = 0; i < nums.length; i++) {

            if (i - k < 0) {
                result[i] = -1;
                continue;
            }
            if (i + k >= nums.length) {
                result[i] = -1;
                continue;
            }
            int sum = 0;
            int count = 0;
            while (left <= right && left < nums.length) {
                sum += nums[left];
                left++;
                count++;
            }
            middle++;
            left = middle - k;
            right = middle + k;
            result[i] = sum / count;
        }
        return result;
    }
}

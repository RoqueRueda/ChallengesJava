package com.roque.rueda;

import java.util.Scanner;

public class MinMaxSum {

    /**
     * https://www.hackerrank.com/contests/wizeline-academy-course/challenges/mini-max-sum
     */

    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        long[] numbers = new long[5];
        for (int i = 0; i < 5; i ++) {
            numbers[i] = in.nextLong();
        }

        calculateMaxAndMin(numbers);

    }

    static void calculateMaxAndMin(long[] numbers) {

        // Find the min, max, and the sum of all

        long min = numbers[0], max = 0, sum = 0;
        for (long n :
                numbers) {
            // max
            if (max < n) {
                max = n;
            }

            // min
            if (min > n) {
                min = n;
            }

            // sum
            sum += n;
        }

        long minSum = sum - min;
        long maxSum = sum - max;

        // the result is the sum of all minus the min and then sum minus the max
        System.out.println(maxSum+ " " + minSum);
    }
}

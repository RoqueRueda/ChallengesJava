package com.roque.rueda;

import java.util.Scanner;

public class BirthdayCakeCandles {

    /**
     * https://www.hackerrank.com/contests/wizeline-academy-course/challenges/birthday-cake-candles
     */

    static int birthdayCakeCandles(int numberOfCandles, int[] candles) {
        // Find the tallest candle
        int maxCandle = 0;
        for (int i = 0; i < numberOfCandles; i++) {
            if (maxCandle < candles[i]) {
                maxCandle = candles[i];
            }
        }

        // Find how many candles have the height
        int result = 0;
        for (int i = 0; i < numberOfCandles; i++) {
            if (candles[i] == maxCandle) {
                result = result + 1;
            }
        }

        // return the number of candles found.
        return result;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int numberOfCandles = in.nextInt();
        int[] candles = new int[numberOfCandles];
        for(int index = 0; index < numberOfCandles; index++){
            candles[index] = in.nextInt();
        }
        int result = birthdayCakeCandles(numberOfCandles, candles);
        System.out.println(result);
    }

}

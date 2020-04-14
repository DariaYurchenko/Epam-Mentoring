package com.epam.exercises.passwordcracker;

import java.util.stream.IntStream;

public class PasswordCracker {
    private static final String HASH_TO_CRACK = "4fd0101ea3d0f5abbe296ef97f47afec";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int ALPHABET_LENGTH = ALPHABET.length();
    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static long startTime;

    private volatile String found;

    public static void main(String[] args) {
        PasswordCracker cracker = new PasswordCracker();
        startTime = System.currentTimeMillis();

        IntStream.range(0, CORES)
                .forEach(threadNumber -> new Thread(() -> cracker.crackPassword(threadNumber)).start());
    }

    private void crackPassword(int threadNumber) {
        int numberToCheck = threadNumber;
        String password;
        while(found == null) {
            password = convertNumberToWord(numberToCheck);
            if(HashCalculator.hash(password).equals(HASH_TO_CRACK)) {
                found = password;
                double time = (System.currentTimeMillis() - startTime) / 1000.0;
                System.out.println("Password is: " + found +", time is " + time + " seconds.");
                break;
            }
            numberToCheck += CORES;
        }
    }

    private String convertNumberToWord(int number) {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(ALPHABET.charAt(number % ALPHABET_LENGTH));
            number /= ALPHABET_LENGTH;
        } while (number != 0);
        return builder.toString();
    }

}

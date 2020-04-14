package com.epam.exercises.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiFunction;

public class Calculator {
    private static final String CORRECT_INPUT = "(\\d+[-+*/])+[\\d]+";
    private static final String SYMBOLS_TO_REPLACE = "[^-+*/0-9]";
    private static final String SPLIT_TO_NUMBERS = "[-+*/]";
    private static final String SPLIT_TO_OPERATORS = "\\d+";
    private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = Map.ofEntries(
            Map.entry("+", (x, y) -> x + y),
            Map.entry("-", (x, y) -> x - y),
            Map.entry("/", (x, y) -> x / y),
            Map.entry("*", (x, y) -> x * y)
    );

    private List<String> operators;
    private List<String> numbers;

    public int calculate(String line) throws IllegalArgumentException {
        convertLineToLists(line);
        int result = Integer.parseInt(numbers.get(0));
        for(int i = 1; i < numbers.size(); i++) {
            result = OPERATIONS.get(operators.get(i-1)).apply(result, Integer.parseInt(numbers.get(i)));
        }
        return result;
    }

    private void convertLineToLists (String line) throws IllegalArgumentException {
        line = line.replaceAll(SYMBOLS_TO_REPLACE, "");
        validateInput(line);
        numbers = new ArrayList<>(Arrays.asList(line.split(SPLIT_TO_NUMBERS)));
        operators = new ArrayList<>(Arrays.asList(line.split(SPLIT_TO_OPERATORS)));
        operators.removeIf(""::equals);
    }

    private void validateInput(String line) {
        if(!line.matches(CORRECT_INPUT)) {
            throw new IllegalArgumentException("You have put incorrect input: you should keep correct sequence of numbers and operations.");
        }
    }

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        System.out.println("Enter an expression: ");
        String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println("The result is " + calculator.calculate(line));
    }

}

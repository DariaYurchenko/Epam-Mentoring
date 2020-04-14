package com.epam.exercises.cleaningcode.exercise3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EightQueens {

    public static void enumerateSolutions(int n) {
        List<List<Integer>> solutions = new ArrayList<>();
        printQueens(findSolutions(solutions, n), n);
    }

    private static List<List<Integer>> findSolutions(List<List<Integer>> solutions, int n) {
        addSolutions(solutions, makeList(n), 0);
        return solutions;
    }

    private static void addSolutions(List<List<Integer>> solutions, List<Integer> solution, int column) {
        if (column == solution.size()) {
            solutions.add(new ArrayList<>(solution));
        } else {
            IntStream.range(0, solution.size()).forEach(row -> {
                solution.set(column, row);
                if (positionIsSafe(solution, column)) {
                    addSolutions(solutions, solution, column + 1);
                }
            });
        }
    }

    private static List<Integer> makeList(int n) {
        return IntStream.range(0, n).mapToObj(i -> (Integer) null).collect(Collectors.toList());
    }

    private static boolean positionIsSafe(List<Integer> queensLocation, int column) {
        return IntStream.range(0, column)
            .noneMatch(position -> checkIfPositionIsNotSafe(queensLocation, position, column));
    }

    private static boolean checkIfPositionIsNotSafe(List<Integer> queensLocation, int position, int column) {
        boolean queensInTheSameRow = queensLocation.get(position).equals(queensLocation.get(column));
        boolean queensOnTheSameDiagonal = Math.abs(queensLocation.get(position) - queensLocation.get(column)) == Math.abs(position - column);
        return queensInTheSameRow || queensOnTheSameDiagonal;
    }

    private static void printQueens(List<List<Integer>> solutions, int n) {
        System.out.println("There are " + solutions.size() + " solutions.");
        IntStream.range(0, solutions.size())
            .peek(i -> System.out.println("\nSolution " + (i + 1)))
            .boxed()
            .flatMap(i -> solutions.get(i).stream())
            .peek(value -> IntStream.range(0, n).forEach(i -> printSymbol(value, i)))
            .forEach(i -> System.out.println());
    }

    private static void printSymbol(Integer value, int i) {
        if (value == i) {
            System.out.print("q");
        } else {
            System.out.print(".");
        }
    }

    public static void main(String[] args) {
        enumerateSolutions(8);
    }

}

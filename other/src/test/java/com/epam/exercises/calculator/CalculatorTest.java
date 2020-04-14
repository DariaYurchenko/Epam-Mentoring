package com.epam.exercises.calculator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void shouldPlusNumbers() {
        String input = "5+1";
        int expected = 6;
        int actual = calculator.calculate(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMinusNumbers() {
        String input = "5-1";
        int expected = 4;
        int actual = calculator.calculate(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldDivideNumbers() {
        String input = "5/1";
        int expected = 5;
        int actual = calculator.calculate(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMultiplyNumbers() {
        String input = "5*2";
        int expected = 10;
        int actual = calculator.calculate(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMakeDifferentOperationsWithNumbers() {
        String input = "50*2/5+10-2";
        int expected = 28;
        int actual = calculator.calculate(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldCutIncorrectSymbols() {
        String input = "5v+&6";
        int expected = 11;
        int actual = calculator.calculate(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionFailInputValidating() {
        String input = "2+4-";
        try {
            calculator.calculate(input);
            Assert.fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("You have put incorrect input: you should keep correct sequence of numbers and operations.", e.getMessage());
        }
    }

    @Test(expected = ArithmeticException.class)
    public void shouldThrowArithmeticException() {
        String input = "5/0";
        calculator.calculate(input);
    }

}

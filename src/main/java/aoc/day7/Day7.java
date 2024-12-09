package aoc.day7;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day7 implements DailyExercise {
    private static final char[] OPERATORS_1 = {'+', '*'};
    private static final char[] OPERATORS_2 = {'+', '*', '|'};

    private boolean isValidRecursive(List<Long> numbers, long target, int i, char[] operators, long current) {
        if (i == numbers.size() - 1) {
            return current == target;
        }

        for (char operator : operators) {
            if (isValidRecursive(numbers, target, i + 1, operators, evaluate(current, numbers.get(i + 1), operator))) {
                return true;
            }
        }
        return false;
    }

    private long evaluate(long a, long b, char operator) {
        return switch (operator) {
            case '+' -> a + b;
            case '*' -> a * b;
            case '|' -> Long.parseLong(String.format("%d%d", a, b));
            default -> a;
        };
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput();
        return Long.toString(input.getCalculationList().stream()
                .filter(calculation -> isValidRecursive(
                        calculation.getValues(),
                        calculation.getResult(),
                        0,
                        OPERATORS_1,
                        calculation.getValues().getFirst())
                )
                .mapToLong(Calculation::getResult)
                .sum());
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput();
        return Long.toString(input.getCalculationList().stream()
                .filter(calculation -> isValidRecursive(
                        calculation.getValues(),
                        calculation.getResult(),
                        0,
                        OPERATORS_2,
                        calculation.getValues().getFirst())
                )
                .mapToLong(Calculation::getResult)
                .sum());
    }

    private Input getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day7.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return new Input(reader.lines()
                    .map(line -> {
                        String[] splittedLines = line.split(" ");
                        return new Calculation(
                                Long.parseLong(splittedLines[0].replace(":", "")),
                                Arrays.stream(splittedLines).skip(1).map(Long::parseLong).toList());
                    }).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

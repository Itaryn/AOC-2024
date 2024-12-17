package aoc.day13;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 implements DailyExercise {
    private final String buttonRegex = "Button [AB]: X\\+(\\d*), Y\\+(\\d*)";
    private final String prizeRegex = "Prize: X=(\\d*), Y=(\\d*)";

    private List<Machine> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day13.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            List<Machine> machines = new ArrayList<>();
            List<String> lines = reader.lines().toList();
            for (int i = 0; i < lines.size(); i += 4) {
                Matcher buttonAMatcher = Pattern.compile(buttonRegex).matcher(lines.get(i));
                Position buttonA;
                Position buttonB;
                Position prize;
                if (buttonAMatcher.find()) {
                    buttonA = new Position(Integer.parseInt(buttonAMatcher.group(1)), Integer.parseInt(buttonAMatcher.group(2)));
                } else {
                    throw new RuntimeException();
                }

                Matcher buttonBMatcher = Pattern.compile(buttonRegex).matcher(lines.get(i + 1));
                if (buttonBMatcher.find()) {
                    buttonB = new Position(Integer.parseInt(buttonBMatcher.group(1)), Integer.parseInt(buttonBMatcher.group(2)));
                } else {
                    throw new RuntimeException();
                }

                Matcher prizeMatcher = Pattern.compile(prizeRegex).matcher(lines.get(i + 2));
                if (prizeMatcher.find()) {
                    prize = new Position(Integer.parseInt(prizeMatcher.group(1)), Integer.parseInt(prizeMatcher.group(2)));
                } else {
                    throw new RuntimeException();
                }

                machines.add(new Machine(buttonA, buttonB, prize));
            }
            return machines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long[] resolve(Machine machine) {
        long x = machine.getPrize().getX();
        long y = machine.getPrize().getY();
        long d = machine.getButtonA().getX();
        long e = machine.getButtonB().getX();
        long f = machine.getButtonA().getY();
        long g = machine.getButtonB().getY();
        long a = (e * y - g * x) / (e * f - g * d);
        long b = (y - f * a) / g;

        if (x != d * a + e * b || y != f * a + g * b) {
            return new long[]{};
        }

        return new long[]{a, b};
    }

    @Override
    public String getFirstAnswer() {
        List<Machine> machines = getInput();
        long cost = machines.stream()
                .map(this::resolve).filter(res -> res.length > 0)
                .mapToLong(res -> res[0] * 3 + res[1])
                .sum();
        return Long.toString(cost);
    }

    @Override
    public String getSecondAnswer() {
        List<Machine> machines = getInput();
        machines.forEach(machine -> machine.getPrize().addToX(10000000000000L).addToY(10000000000000L));
        long cost = machines.stream()
                .map(this::resolve).filter(res -> res.length > 0)
                .mapToLong(res -> res[0] * 3L + res[1])
                .sum();
        return Long.toString(cost);
    }
}

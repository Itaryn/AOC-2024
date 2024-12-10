package aoc.day10;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day10 implements DailyExercise {
    @Override
    public String getFirstAnswer() {
        List<List<Integer>> input = getInput();
        return Long.toString(getHikingPositions(input).stream().distinct().count());
    }

    @Override
    public String getSecondAnswer() {
        List<List<Integer>> input = getInput();
        return Long.toString(getHikingPositions(input).size());
    }

    private List<Position> getHikingPositions(List<List<Integer>> input) {
        List<Position> total = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            List<Integer> line = input.get(y);
            for (int x = 0; x < line.size(); x++) {
                if (line.get(x) == 0) {
                    List<Position> positions = getHikingScore(input, x, y, 0);
                    for (Position position : positions) {
                        position.setStartX(x).setStartY(y);
                    }
                    total.addAll(positions);
                }
            }
        }
        return total;
    }

    private List<Position> getHikingScore(List<List<Integer>> input, int x, int y, int height) {
        if (y < 0 || y >= input.size() || x < 0 || x >= input.get(y).size() || input.get(y).get(x) != height) {
            return List.of();
        }
        if (input.get(y).get(x) == 9) {
            return List.of(new Position(x, y));
        }
        return Stream.of(getHikingScore(input, x + 1, y, height + 1),
                        getHikingScore(input, x - 1, y, height + 1),
                        getHikingScore(input, x, y + 1, height + 1),
                        getHikingScore(input, x, y - 1, height + 1))
                .flatMap(Collection::stream)
                .toList();
    }

    private List<List<Integer>> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day10.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().map(line -> line.chars().mapToObj(Character::getNumericValue).toList()).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

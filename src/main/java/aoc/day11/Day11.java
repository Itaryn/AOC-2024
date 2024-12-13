package aoc.day11;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day11 implements DailyExercise {
    private final Map<String, Long> cache = new HashMap<>();

    @Override
    public String getFirstAnswer() {
        List<Long> stones = getInput();
        return Long.toString(stones.stream().mapToLong(stone -> count(stone, 25)).sum());
    }

    @Override
    public String getSecondAnswer() {
        List<Long> stones = getInput();
        return Long.toString(stones.stream().mapToLong(stone -> count(stone, 75)).sum());
    }

    private List<Long> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day11.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return Arrays.stream(reader.readLine().split(" ")).map(Long::parseLong).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long count(long stone, int blinkings) {
        String key = stone + "-" + blinkings;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        long result;
        if (blinkings == 0) {
            result = 1;
        } else if (stone == 0) {
            result = count(1, blinkings - 1);
        } else if (Long.toString(stone).length() % 2 == 0) {
            result = count(Long.parseLong(Long.toString(stone).substring(Long.toString(stone).length() / 2)), blinkings - 1) +
                    count(Long.parseLong(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)), blinkings - 1);
        } else {
            result = count(stone * 2024, blinkings - 1);
        }

        cache.put(key, result);
        return result;
    }
}

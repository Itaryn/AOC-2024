package aoc.day3;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements DailyExercise {
    public static final String MUL_REGEX = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

    @Override
    public String getFirstAnswer() {
        List<Mul> muls = getMuls(readFile());
        int total = muls.stream().mapToInt(Mul::getResult).sum();
        return Integer.toString(total);
    }

    @Override
    public String getSecondAnswer() {
        List<Mul> muls = getOnlyDo(readFile()).stream()
                .map(this::getMuls)
                .flatMap(List::stream)
                .toList();
        int total = muls.stream().mapToInt(Mul::getResult).sum();
        return Integer.toString(total);
    }

    private String readFile() {
        try {
            return Files.readString(Path.of("./src/aoc/day3/input.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Mul> getMuls(String input) {
        List<Mul> muls = new ArrayList<>();
        Matcher m = Pattern.compile(MUL_REGEX).matcher(input);
        while (m.find()) {
            muls.add(new Mul().setVal1(Integer.parseInt(m.group(1))).setVal2(Integer.parseInt(m.group(2))));
        }

        return muls;
    }

    private List<String> getOnlyDo(String input) {
        List<String> validLines = new ArrayList<>();
        String[] splitByDo = input.split("do\\(\\)");
        for (String line: splitByDo) {
            String[] splitByDont = line.split("don't\\(\\)");
            validLines.add(splitByDont[0]);
        }
        return validLines;
    }
}

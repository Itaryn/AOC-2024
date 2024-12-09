package aoc.day4;

import aoc.DailyExercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day4 implements DailyExercise {
    public static final String WORD = "XMAS";
    public static final String WORD_2 = "MAS";

    private static boolean haveSize(int pos) {
        return pos >= WORD.length() - 1;
    }

    private static boolean isSouthValid(List<String> input, int y) {
        return y <= input.size() - WORD.length();
    }

    private static boolean isEastValid(List<String> input, int x, int y) {
        return x <= input.get(y).length() - WORD.length();
    }

    @Override
    public String getFirstAnswer() {
        return Integer.toString(countWords(readFile()));
    }

    @Override
    public String getSecondAnswer() {
        return Integer.toString(countWords2(readFile()));
    }

    private List<String> readFile() {
        try {
            return Files.readAllLines(Path.of("./src/aoc/day4/input.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int countWords(List<String> input) {
        int res = 0;

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == WORD.charAt(0)) {
                    res += countWordsNearby(input, x, y);
                }
            }
        }

        return res;
    }

    private int countWords2(List<String> input) {
        int res = 0;

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == WORD_2.charAt(1)) {
                    res += countXWords(input, x, y);
                }
            }
        }

        return res;
    }

    private int countWordsNearby(List<String> input, int x, int y) {
        int res = 0;
        boolean northValid = haveSize(y);
        boolean eastValid = isEastValid(input, x, y);
        boolean southValid = isSouthValid(input, y);
        boolean westValid = haveSize(x);

        // N
        if (northValid && isWordFound(input, x, y, 0, -1)) {
            res++;
        }

        // NE
        if (northValid && eastValid && isWordFound(input, x, y, 1, -1)) {
            res++;
        }

        // E
        if (eastValid && isWordFound(input, x, y, 1, 0)) {
            res++;
        }

        // SE
        if (southValid && eastValid && isWordFound(input, x, y, 1, 1)) {
            res++;
        }

        // S
        if (southValid && isWordFound(input, x, y, 0, 1)) {
            res++;
        }

        // SW
        if (southValid && westValid && isWordFound(input, x, y, -1, 1)) {
            res++;
        }

        // W
        if (westValid && isWordFound(input, x, y, -1, 0)) {
            res++;
        }

        // NW
        if (northValid && westValid && isWordFound(input, x, y, -1, -1)) {
            res++;
        }

        return res;
    }

    private boolean isWordFound(List<String> input, int posX, int posY, int xMove, int yMove) {
        int x = posX;
        int y = posY;
        for (char c : WORD.toCharArray()) {
            if (input.get(y).charAt(x) != c) {
                return false;
            }
            x += xMove;
            y += yMove;
        }
        return true;
    }

    private int countXWords(List<String> input, int x, int y) {
        int res = 0;
        boolean northValid = y >= 1;
        boolean eastValid = x < input.get(y).length() - 1;
        boolean southValid = y < input.size() - 1;
        boolean westValid = x >= 1;

        if (northValid && eastValid && southValid && westValid) {
            String upperLine = input.get(y - 1);
            String aboveLine = input.get(y + 1);
            String word1 = new String(new char[]{upperLine.charAt(x - 1), WORD_2.charAt(1), aboveLine.charAt(x + 1)});
            String word2 = new String(new char[]{upperLine.charAt(x + 1), WORD_2.charAt(1), aboveLine.charAt(x - 1)});
            if ((word1.equals(WORD_2) || new StringBuilder(word1).reverse().toString().equals(WORD_2)) &&
                    (word2.equals(WORD_2) || new StringBuilder(word2).reverse().toString().equals(WORD_2))) {
                res++;
            }
        } else {
            res = 0;
        }

        return res;
    }
}

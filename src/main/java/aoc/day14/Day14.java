package aoc.day14;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 implements DailyExercise {
    public static final int X_LIMIT = 101;
    public static final int Y_LIMIT = 103;
    public static final int SECONDS = 100;
    private final String botRegex = "p=(\\d*),(\\d*) v=(-?\\d*),(-?\\d*)";
    private final List<boolean[][]> PATTERNS = List.of(
            new boolean[][]{
                    {false, false, true, false, false},
                    {false, false, true, false, false},
                    {false, true, true, true, false},
                    {true, true, true, true, true},
                    {false, false, true, false, false}
            },
            new boolean[][]{
                    {false, true, false, false, false},
                    {false, true, true, false, false},
                    {true, true, true, true, true},
                    {false, true, true, false, false},
                    {false, true, false, false, false}
            },
            new boolean[][]{
                    {false, false, true, false, false},
                    {true, true, true, true, true},
                    {false, true, true, true, false},
                    {false, false, true, false, false},
                    {false, false, true, false, false}
            },
            new boolean[][]{
                    {false, false, false, true, false},
                    {false, false, true, true, false},
                    {true, true, true, true, true},
                    {false, false, true, true, false},
                    {false, false, false, true, false}
            }
    );

    private List<Bot> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day14.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().map(line -> {
                Matcher matcher = Pattern.compile(botRegex).matcher(line);
                if (matcher.find()) {
                    return new Bot(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                } else {
                    throw new RuntimeException();
                }
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void move(Bot bot, int seconds, int xLimit, int yLimit) {
        for (int i = 0; i < seconds; i++) {
            int x = bot.getX() + bot.getMoveX();
            int y = bot.getY() + bot.getMoveY();
            if (x < 0) {
                x += xLimit;
            } else if (x >= xLimit) {
                x -= xLimit;
            }
            if (y < 0) {
                y += yLimit;
            } else if (y >= yLimit) {
                y -= yLimit;
            }
            bot.setX(x);
            bot.setY(y);
        }
    }

    private long numberOfRobots(List<Bot> bots, int xLimit, int yLimit) {
        return bots.stream().filter(bot -> bot.getX() < xLimit / 2 && bot.getY() < yLimit / 2).count() *
                bots.stream().filter(bot -> bot.getX() >= xLimit / 2 + 1 && bot.getY() >= yLimit / 2 + 1).count() *
                bots.stream().filter(bot -> bot.getX() < xLimit / 2 && bot.getY() >= yLimit / 2 + 1).count() *
                bots.stream().filter(bot -> bot.getX() >= xLimit / 2 + 1 && bot.getY() < yLimit / 2).count();
    }

    private boolean[][] toMap(List<Bot> bots, int xLimit, int yLimit) {
        boolean[][] map = new boolean[yLimit][xLimit];

        for (Bot bot : bots) {
            map[bot.getY()][bot.getX()] = true;
        }

        return map;
    }

    /**
     * Return true if any of the pattern is found in the map
     * All the patterns should be of the same length
     *
     * @param map      the map of robots
     * @param patterns list of patterns to find
     * @return True if any pattern is found
     */
    private boolean foundPattern(boolean[][] map, List<boolean[][]> patterns) {
        int patternYLength = patterns.getFirst().length;
        int patternXLength = patterns.getFirst()[0].length;
        for (int y = 0; y < map.length - patternYLength; y++) {
            for (int x = 0; x < map[y].length - patternXLength; x++) {
                for (boolean[][] pattern : patterns) {
                    int currY = y;
                    boolean valid = true;
                    while (currY < y + patternYLength && valid) {
                        int currX = x;
                        while (currX < x + patternXLength && valid) {
                            if (pattern[currY - y][currX - x] && !map[currY][currX]) {
                                valid = false;
                            }
                            currX++;
                        }
                        currY++;
                    }
                    if (valid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getFirstAnswer() {
        List<Bot> bots = getInput();
        bots.forEach(bot -> move(bot, SECONDS, X_LIMIT, Y_LIMIT));
        return Long.toString(numberOfRobots(bots, X_LIMIT, Y_LIMIT));
    }

    @Override
    public String getSecondAnswer() {

        List<Bot> bots = getInput();
        int i = 0;
        boolean found = false;
        while (!found) {
            boolean[][] map = toMap(bots, X_LIMIT, Y_LIMIT);
            if (foundPattern(map, PATTERNS)) {
                System.out.printf("Iteration %d%n", i);
                printMap(map);
                found = true;
            }
            bots.forEach(bot -> move(bot, 1, X_LIMIT, Y_LIMIT));
            i++;
        }
        return "";
    }

    private void printMap(boolean[][] map) {
        for (boolean[] row : map) {
            for (boolean cell : row) {
                System.out.print(cell ? 'X' : '-');
            }
            System.out.println();
        }
        System.out.println();
    }
}

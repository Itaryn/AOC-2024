package aoc.day16;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day16 implements DailyExercise {
    private final static Map<String, Long> savedPosition = new HashMap<>();

    private static void updateMaps(char[][] maze, Reindeer reindeer, Map<Reindeer, Long> alreadyCalculated, Map<Reindeer, Long> currentScores, Reindeer current, int score) {
        if (maze[reindeer.getPosition().getY()][reindeer.getPosition().getX()] != '#') {
            if (!alreadyCalculated.containsKey(reindeer) || alreadyCalculated.get(reindeer) > currentScores.get(current) + score) {
                alreadyCalculated.put(reindeer, currentScores.get(current) + score);
                currentScores.put(reindeer, currentScores.get(current) + score);
            }
        }
    }

    private Input getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/input_day16.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            Reindeer reindeer = null;
            List<char[]> maze = new ArrayList<>();
            int y = 0;
            while ((line = reader.readLine()) != null) {
                maze.add(line.replace('S', '.').toCharArray());
                if (line.indexOf('S') >= 0) {
                    reindeer = new Reindeer(new Position(line.indexOf('S'), y), Direction.RIGHT);
                }
                y++;
            }
            if (reindeer == null) {
                throw new RuntimeException("Reindeer not found");
            }
            return new Input(reindeer, maze.toArray(char[][]::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long aStar(char[][] maze, Reindeer start) {
        Map<Reindeer, Long> currentScores = new HashMap<>();
        Map<Reindeer, Long> alreadyCalculated = new HashMap<>();
        currentScores.put(start, 0L);
        while (!currentScores.isEmpty()) {
            Reindeer current = Collections.min(currentScores.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
            if (maze[current.getPosition().getY()][current.getPosition().getX()] == 'E') {
                return currentScores.get(current);
            }
            Reindeer neighbourTop = new Reindeer(new Position(current.getPosition().getX(), current.getPosition().getY() - 1), Direction.TOP);
            Reindeer neighbourRight = new Reindeer(new Position(current.getPosition().getX() + 1, current.getPosition().getY()), Direction.RIGHT);
            Reindeer neighbourDown = new Reindeer(new Position(current.getPosition().getX(), current.getPosition().getY() + 1), Direction.DOWN);
            Reindeer neighbourLeft = new Reindeer(new Position(current.getPosition().getX() - 1, current.getPosition().getY()), Direction.LEFT);
            switch (current.getDirection()) {
                case TOP -> {
                    updateMaps(maze, neighbourTop, alreadyCalculated, currentScores, current, 1);
                    updateMaps(maze, neighbourLeft, alreadyCalculated, currentScores, current, 1001);
                    updateMaps(maze, neighbourRight, alreadyCalculated, currentScores, current, 1001);
                }
                case RIGHT -> {
                    updateMaps(maze, neighbourRight, alreadyCalculated, currentScores, current, 1);
                    updateMaps(maze, neighbourTop, alreadyCalculated, currentScores, current, 1001);
                    updateMaps(maze, neighbourDown, alreadyCalculated, currentScores, current, 1001);
                }
                case DOWN -> {
                    updateMaps(maze, neighbourDown, alreadyCalculated, currentScores, current, 1);
                    updateMaps(maze, neighbourLeft, alreadyCalculated, currentScores, current, 1001);
                    updateMaps(maze, neighbourRight, alreadyCalculated, currentScores, current, 1001);
                }
                case LEFT -> {
                    updateMaps(maze, neighbourLeft, alreadyCalculated, currentScores, current, 1);
                    updateMaps(maze, neighbourTop, alreadyCalculated, currentScores, current, 1001);
                    updateMaps(maze, neighbourDown, alreadyCalculated, currentScores, current, 1001);
                }
            }
            currentScores.remove(current);
        }
        return -1;
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput();
        return Long.toString(aStar(input.getMaze(), input.getReindeer()));
    }

    @Override
    public String getSecondAnswer() {
        return "";
    }
}

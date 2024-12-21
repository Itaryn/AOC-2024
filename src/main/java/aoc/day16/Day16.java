package aoc.day16;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day16 implements DailyExercise {
    private List<Path> result;

    private static void updateMaps(char[][] maze, Reindeer reindeer, List<Map.Entry<Reindeer, Path>> alreadyCalculated,
                                   List<Map.Entry<Reindeer, Path>> currentScores, Map.Entry<Reindeer, Path> current, int score) {
        if (maze[reindeer.getPosition().getY()][reindeer.getPosition().getX()] != '#') {
            Optional<Map.Entry<Reindeer, Path>> calculated = alreadyCalculated.stream()
                    .filter(c -> c.getKey().equals(reindeer))
                    .findFirst();
            List<Position> path = new ArrayList<>(current.getValue().getPath());
            path.add(reindeer.getPosition());
            if (calculated.isEmpty() || calculated.get().getValue().getScore() > current.getValue().getScore() + score) {
                calculated.ifPresent(alreadyCalculated::remove);
                alreadyCalculated.add(Map.entry(reindeer, new Path(current.getValue().getScore() + score, List.of())));
                currentScores.add(Map.entry(reindeer, new Path(current.getValue().getScore() + score, path)));
            } else if (calculated.get().getValue().getScore() == current.getValue().getScore() + score) {
                alreadyCalculated.add(Map.entry(reindeer, new Path(current.getValue().getScore() + score, path)));
            }
        }
    }

    protected Input getInput(String path) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(path);
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

    protected List<Path> getShortestPaths(char[][] maze, Reindeer start) {
        List<Map.Entry<Reindeer, Path>> currentScores = new ArrayList<>();
        List<Map.Entry<Reindeer, Path>> alreadyCalculated = new ArrayList<>();
        List<Path> bestPaths = new ArrayList<>();
        currentScores.add(Map.entry(start, new Path(0L, List.of(start.getPosition()))));
        while (!currentScores.isEmpty()) {
            currentScores.sort(Comparator.comparingLong(entry -> entry.getValue().getScore()));
            Map.Entry<Reindeer, Path> current = currentScores.getFirst();
            if (bestPaths.isEmpty() || current.getValue().getScore() <= bestPaths.getFirst().getScore()) {
                if (maze[current.getKey().getPosition().getY()][current.getKey().getPosition().getX()] == 'E' && (bestPaths.isEmpty()
                        || bestPaths.getFirst().getScore() == current.getValue().getScore())) {
                    bestPaths.add(current.getValue());
                    Set<Position> checkedPositions = new HashSet<>();
                    for (Position position : current.getValue().getPath().subList(1, current.getValue().getPath().size())) {
                        addOtherPaths(checkedPositions, position, bestPaths, alreadyCalculated);
                    }
                }
                Reindeer neighbourTop = new Reindeer(new Position(current.getKey().getPosition().getX(), current.getKey().getPosition().getY() - 1), Direction.TOP);
                Reindeer neighbourRight = new Reindeer(new Position(current.getKey().getPosition().getX() + 1, current.getKey().getPosition().getY()), Direction.RIGHT);
                Reindeer neighbourDown = new Reindeer(new Position(current.getKey().getPosition().getX(), current.getKey().getPosition().getY() + 1), Direction.DOWN);
                Reindeer neighbourLeft = new Reindeer(new Position(current.getKey().getPosition().getX() - 1, current.getKey().getPosition().getY()), Direction.LEFT);
                switch (current.getKey().getDirection()) {
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
            }
            currentScores.remove(current);
        }
        return bestPaths;
    }

    private void addOtherPaths(Set<Position> checkedPositions, Position position, List<Path> bestPaths, List<Map.Entry<Reindeer, Path>> alreadyCalculated) {
        if (!checkedPositions.contains(position)) {
            checkedPositions.add(position);
            List<Path> otherPaths = alreadyCalculated.stream()
                    .filter(calc -> calc.getKey().getPosition().equals(position))
                    .map(Map.Entry::getValue).toList();
            long minScore = otherPaths.stream().min(Comparator.comparingLong(Path::getScore)).get().getScore();
            List<Path> minPaths = otherPaths.stream().filter(path -> path.getScore() == minScore).toList();
            if (minPaths.size() > 1) {
                bestPaths.addAll(minPaths);
                minPaths.stream().flatMap(path -> path.getPath().stream()).distinct()
                        .forEach(pos -> addOtherPaths(checkedPositions, pos, bestPaths, alreadyCalculated));
            }
        }
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput("/input_day16.txt");
        result = getShortestPaths(input.getMaze(), input.getReindeer());
        return Long.toString(result.getFirst().getScore());
    }

    @Override
    public String getSecondAnswer() {
        return Long.toString(result.stream().flatMap(path -> path.getPath().stream()).distinct().count());
    }
}

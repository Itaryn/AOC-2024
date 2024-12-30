package aoc.day18;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day18 implements DailyExercise {
    protected List<Position> getInput(String path) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().map(line -> {
                String[] splittedLine = line.split(",");
                return new Position(Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1]));

            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<List<Boolean>> generateMap(List<Position> positions, int max, int mapSize) {
        List<List<Boolean>> map = IntStream.range(0, mapSize)
                .mapToObj(i -> IntStream.range(0, mapSize)
                        .mapToObj(j -> false)
                        .collect(Collectors.toList())
                ).toList();
        for (int i = 0; i < Math.min(positions.size(), max); i++) {
            map.get(positions.get(i).getY()).set(positions.get(i).getX(), true);
        }
        return map;
    }

    protected long foundShortestPath(List<List<Boolean>> map) {
        Map<Position, Long> visited = new HashMap<>();
        List<PositionPath> toDo = new ArrayList<>();
        toDo.add(new PositionPath(new Position(0, 0), 0));
        long found = -1L;
        while (!toDo.isEmpty() && found < 0) {
            toDo.sort(Comparator.comparingLong(PositionPath::getLength));
            PositionPath current = toDo.removeFirst();
            if (current.getPosition().equals(new Position(map.size() - 1, map.size() - 1))) {
                found = current.getLength();
            } else if (!visited.containsKey(current.getPosition()) || visited.get(current.getPosition()) > current.getLength()) {
                visited.put(current.getPosition(), current.getLength());
                addNewSearch(map, toDo, current.getPosition().getX() - 1, current.getPosition().getY(), current.getLength());
                addNewSearch(map, toDo, current.getPosition().getX() + 1, current.getPosition().getY(), current.getLength());
                addNewSearch(map, toDo, current.getPosition().getX(), current.getPosition().getY() - 1, current.getLength());
                addNewSearch(map, toDo, current.getPosition().getX(), current.getPosition().getY() + 1, current.getLength());
            }
        }
        return found;
    }

    private void addNewSearch(List<List<Boolean>> map, List<PositionPath> toDo, int x, int y, long length) {
        if (isInMap(x, y, map.size()) && !isAWall(map, x, y)) {
            Position position = new Position(x, y);
            toDo.add(new PositionPath(position, length + 1));
        }
    }

    private boolean isAWall(List<List<Boolean>> map, int x, int y) {
        return map.get(y).get(x);
    }

    private boolean isInMap(int x, int y, int mapSize) {
        return x >= 0 && y >= 0 && x < mapSize && y < mapSize;
    }

    private Position getLockPosition(List<Position> positions, int searchingStart, int mapSize) {
        List<List<Boolean>> map = generateMap(positions, searchingStart, mapSize);
        int i = searchingStart - 1;
        long found;
        do {
            i++;
            map.get(positions.get(i).getY()).set(positions.get(i).getX(), true);
            found = foundShortestPath(map);
        }
        while (i < positions.size() && found > 0);
        return positions.get(i);
    }

    @Override
    public String getFirstAnswer() {
        return Long.toString(foundShortestPath(generateMap(getInput("/day18/input.txt"), 1024, 71)));
    }

    @Override
    public String getSecondAnswer() {
        Position lockPosition = getLockPosition(getInput("/day18/input.txt"), 1024, 71);
        return "%d,%d".formatted(lockPosition.getX(), lockPosition.getY());
    }
}

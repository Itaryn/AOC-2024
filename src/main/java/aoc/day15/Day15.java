package aoc.day15;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day15 implements DailyExercise {
    private void move(Box[][] map, char[] instructions) {
        int y = -1;
        int x = -1;
        int currentY = 0;
        while (y == -1) {
            int currentX = 0;
            while (y == -1 && currentX < map[0].length) {
                if (map[currentY][currentX].getType().equals(BoxType.ROBOT)) {
                    y = currentY;
                    x = currentX;
                }
                currentX++;
            }
            currentY++;
        }
        for (Character instruction : instructions) {
            int[] robotPosition = move(map, x, y, instruction);
            x = robotPosition[0];
            y = robotPosition[1];
        }
    }

    private long calculate(Box[][] map) {
        long total = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x].getType().equals(BoxType.BOX) || map[y][x].getType().equals(BoxType.BOX_LEFT)) {
                    total += 100L * y + x;
                }
            }
        }
        return total;
    }

    private int[] move(Box[][] map, int x, int y, char instruction) {
        int moveX = 0;
        int moveY = 0;
        switch (instruction) {
            case '^' -> moveY = -1;
            case '>' -> moveX = 1;
            case 'v' -> moveY = 1;
            case '<' -> moveX = -1;
        }
        int checkMoveX = 0;
        int checkMoveY = 0;
        while (!map[y + moveY + checkMoveY][x + moveX + checkMoveX].getType().equals(BoxType.EMPTY) &&
                !map[y + moveY + checkMoveY][x + moveX + checkMoveX].getType().equals(BoxType.WALL)) {
            checkMoveX += moveX;
            checkMoveY += moveY;
        }
        if (map[y + moveY + checkMoveY][x + moveX + checkMoveX].getType().equals(BoxType.EMPTY)) {
            if (moveY != 0 && canPushVertically(map, x, y, moveY)) {
                List<Position> moveBoxes = getMoveBox(map, x, y, moveY).stream().distinct().collect(Collectors.toList());
                if (moveY > 0) {
                    moveBoxes.sort((a, b) -> b.getY() - a.getY());
                } else {
                    moveBoxes.sort(Comparator.comparingInt(Position::getY));
                }
                for (Position position : moveBoxes) {
                    Box temp = map[position.getY() + moveY][position.getX()];
                    map[position.getY() + moveY][position.getX()] = map[position.getY()][position.getX()];
                    map[position.getY()][position.getX()] = temp;
                }
            } else if (moveX != 0) {
                for (int i = Math.max(Math.abs(checkMoveX), Math.abs(checkMoveY)); i >= 0; i--) {
                    Box temp = map[y + moveY + i][x + moveX + i * moveX];
                    map[y + moveY + i][x + moveX + i * moveX] = map[y + i][x + i * moveX];
                    map[y + i][x + i * moveX] = temp;
                }
            } else {
                // Revert the change because there is no space
                moveY = 0;
            }
        } else {
            // Revert the change because there is no space
            moveX = 0;
            moveY = 0;
        }
        return new int[]{x + moveX, y + moveY};
    }

    private boolean canPushVertically(Box[][] map, int x, int y, int moveY) {
        if (map[y + moveY][x].getType().equals(BoxType.EMPTY)) {
            return true;
        } else if (map[y + moveY][x].getType().equals(BoxType.BOX_LEFT)) {
            return canPushVertically(map, x, y + moveY, moveY) &&
                    canPushVertically(map, x + 1, y + moveY, moveY);
        } else if (map[y + moveY][x].getType().equals(BoxType.BOX_RIGHT)) {
            return canPushVertically(map, x, y + moveY, moveY) &&
                    canPushVertically(map, x - 1, y + moveY, moveY);
        } else if (map[y + moveY][x].getType().equals(BoxType.BOX)) {
            return canPushVertically(map, x, y + moveY, moveY);
        }
        return false;
    }

    private List<Position> getMoveBox(Box[][] map, int x, int y, int moveY) {
        List<Position> res = new ArrayList<>();
        res.add(new Position(x, y));
        if (map[y + moveY][x].getType().equals(BoxType.BOX_LEFT)) {
            res.addAll(getMoveBox(map, x, y + moveY, moveY));
            res.addAll(getMoveBox(map, x + 1, y + moveY, moveY));
        } else if (map[y + moveY][x].getType().equals(BoxType.BOX_RIGHT)) {
            res.addAll(getMoveBox(map, x, y + moveY, moveY));
            res.addAll(getMoveBox(map, x - 1, y + moveY, moveY));
        } else if (map[y + moveY][x].getType().equals(BoxType.BOX)) {
            res.addAll(getMoveBox(map, x, y + moveY, moveY));
        }
        return res;
    }

    private Box[][] doubleTheMap(Box[][] map) {
        List<List<Box>> newMap = new ArrayList<>();
        for (Box[] line : map) {
            List<Box> newLine = new ArrayList<>();
            for (Box box : line) {
                switch (box.getType()) {
                    case BOX -> {
                        newLine.add(new Box(BoxType.BOX_LEFT));
                        newLine.add(new Box(BoxType.BOX_RIGHT));
                    }
                    case ROBOT -> {
                        newLine.add(new Box(BoxType.ROBOT));
                        newLine.add(new Box(BoxType.EMPTY));
                    }
                    default -> {
                        newLine.add(box);
                        newLine.add(box);
                    }
                }
            }
            newMap.add(newLine);
        }
        return newMap.stream().map(line -> line.toArray(Box[]::new)).toArray(Box[][]::new);
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput();
        move(input.getBoxes(), input.getInstructions().toCharArray());
        return Long.toString(calculate(input.getBoxes()));
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput();
        Box[][] doubleMap = doubleTheMap(input.getBoxes());
        move(doubleMap, input.getInstructions().toCharArray());
        return Long.toString(calculate(doubleMap));
    }

    private Input getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/input_day15.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            boolean firstPart = true;
            List<Box[]> boxes = new ArrayList<>();
            StringBuilder instructions = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    firstPart = false;
                    continue;
                }
                if (firstPart) {
                    boxes.add(line.chars().mapToObj(c -> new Box(BoxType.of((char) c))).toList().toArray(Box[]::new));
                } else {
                    instructions.append(line);
                }
            }
            return new Input(boxes.toArray(Box[][]::new), instructions.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

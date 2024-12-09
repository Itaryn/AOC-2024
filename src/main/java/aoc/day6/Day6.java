package aoc.day6;

import aoc.DailyExercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day6 implements DailyExercise {

    private static boolean isInsideMap(int x, int y, Input input) {
        return x >= 0 && y >= 0 && x < input.getMap().getFirst().size() && y < input.getMap().size();
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput();
        runGuard(input);
        return Long.toString(input.getMap().stream().flatMap(line -> line.stream().filter(Box::hasBeenVisited)).count());
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput();
        int maxX = input.getMap().getFirst().size();
        int maxY = input.getMap().size();
        int totalTry = maxY * maxX;
        int currentTry = 1;
        int loop = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                Box box = input.getMap().get(y).get(x);
                System.out.printf("Test %d/%d\n", currentTry++, totalTry);
                if (!box.isAnObstacle()) {
                    Input clone = input.clone();
                    clone.getMap().get(y).get(x).addObstacle();
                    if (runGuard(clone)) {
                        System.out.printf("x: %d and y: %d is a loop", x, y);
                        loop++;
                    }
                }
            }
        }
        return Integer.toString(loop);
    }

    private Input getInput() {
        try {
            List<String> lines = Files.readAllLines(Path.of("./src/aoc/day6/input.txt"));
            Guard guard = null;
            List<List<Box>> map = new ArrayList<>();
            for (int y = 0; y < lines.size(); y++) {
                List<Box> mapLine = new ArrayList<>();
                String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if (c == '^' || c == 'v' || c == '>' || c == '<') {
                        guard = new Guard(x, y, c);
                    }
                    mapLine.add(new Box(c == '#'));
                }
                map.add(mapLine);
            }
            if (guard == null) {
                throw new RuntimeException("Guard is not present");
            }
            return new Input(map, guard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean runGuard(Input input) {
        boolean alreadyVisited = false;
        Guard guard = input.getGuard();
        int x = guard.getX();
        int y = guard.getY();
        int movX = 0;
        int movY = 0;
        while (!alreadyVisited && isInsideMap(x, y, input)) {
            movX = guard.getDirection() == '>' ? 1 : guard.getDirection() == '<' ? -1 : 0;
            movY = guard.getDirection() == '^' ? -1 : guard.getDirection() == 'v' ? 1 : 0;
            Box lastPos = input.getMap().get(y).get(x);
            x = x + movX;
            y = y + movY;
            if (isInsideMap(x, y, input)) {
                Box currentPos = input.getMap().get(y).get(x);
                if (currentPos.isAnObstacle()) {
                    x = x - movX;
                    y = y - movY;
                    if (currentPos.hasBeenVisited(guard.getDirection())) {
                        alreadyVisited = true;
                    } else {
                        currentPos.visit(guard.getDirection());
                        lastPos.visit(guard.getDirection());
                        guard.moveRight();
                    }
                } else {
                    lastPos.visit(guard.getDirection());
                }
            }
        }
        // Visit the last position;
        input.getMap().get(y - movY).get(x - movX).visit(guard.getDirection());
        return alreadyVisited;
    }
}

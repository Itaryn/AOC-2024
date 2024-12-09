package aoc.day8;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day8 implements DailyExercise {
    private final static char EMPTY = '.';

    @Override
    public String getFirstAnswer() {
        List<List<Box>> map = getInput();
        int maxX = map.getFirst().size();
        int maxy = map.size();
        map.stream().flatMap(line -> line.stream().filter(box -> box.getValue() != EMPTY))
                .collect(Collectors.groupingBy(Box::getValue))
                .forEach((c, antennas) -> {
                    if (antennas.size() > 1) {
                        for (int i = 0; i < antennas.size() - 1; i++) {
                            for (int j = i + 1; j < antennas.size(); j++) {
                                Box antenna1 = antennas.get(i);
                                Box antenna2 = antennas.get(j);
                                int difX = antenna1.getX() - antenna2.getX();
                                int difY = antenna1.getY() - antenna2.getY();
                                int posX1 = antenna1.getX() + difX;
                                int posY1 = antenna1.getY() + difY;
                                int posX2 = antenna2.getX() - difX;
                                int posY2 = antenna2.getY() - difY;
                                if (posX1 >= 0 && posX1 < maxX && posY1 >= 0 && posY1 < maxy) {
                                    map.get(posY1).get(posX1).setAntinode();
                                }
                                if (posX2 >= 0 && posX2 < maxX && posY2 >= 0 && posY2 < maxy) {
                                    map.get(posY2).get(posX2).setAntinode();
                                }

                            }
                        }
                    }
                });
        return Long.toString(map.stream().flatMap(line -> line.stream().filter(Box::isAntinode)).count());
    }

    @Override
    public String getSecondAnswer() {
        List<List<Box>> map = getInput();
        int maxX = map.getFirst().size();
        int maxy = map.size();
        map.stream().flatMap(line -> line.stream().filter(box -> box.getValue() != EMPTY))
                .collect(Collectors.groupingBy(Box::getValue))
                .forEach((c, antennas) -> {
                    if (antennas.size() > 1) {
                        for (int i = 0; i < antennas.size() - 1; i++) {
                            for (int j = i + 1; j < antennas.size(); j++) {
                                Box antenna1 = antennas.get(i);
                                Box antenna2 = antennas.get(j);
                                antenna1.setAntinode();
                                antenna2.setAntinode();
                                int difX = antenna1.getX() - antenna2.getX();
                                int difY = antenna1.getY() - antenna2.getY();
                                int posX1 = antenna1.getX();
                                int posY1 = antenna1.getY();
                                do {
                                    map.get(posY1).get(posX1).setAntinode();
                                    posX1 += difX;
                                    posY1 += difY;
                                }
                                while (posX1 >= 0 && posX1 < maxX && posY1 >= 0 && posY1 < maxy);

                                int posX2 = antenna2.getX();
                                int posY2 = antenna2.getY();
                                do {
                                    map.get(posY2).get(posX2).setAntinode();
                                    posX2 -= difX;
                                    posY2 -= difY;
                                }
                                while (posX2 >= 0 && posX2 < maxX && posY2 >= 0 && posY2 < maxy);
                            }
                        }
                    }
                });
        return Long.toString(map.stream().flatMap(line -> line.stream().filter(Box::isAntinode)).count());
    }

    private List<List<Box>> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day8.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            List<String> lines = reader.lines().toList();
            List<List<Box>> map = new ArrayList<>();
            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                List<Box> mapLine = new ArrayList<>();
                for (int x = 0; x < line.length(); x++) {
                    mapLine.add(new Box(line.charAt(x), x, y));
                }
                map.add(mapLine);
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

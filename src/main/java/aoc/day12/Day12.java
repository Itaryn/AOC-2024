package aoc.day12;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day12 implements DailyExercise {
    private List<List<Box>> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day12.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().map(line -> line.chars().mapToObj(c -> new Box((char) c)).toList()).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<List<Box>> groupByRegion(List<List<Box>> garden) {
        List<List<Box>> regions = new ArrayList<>();
        for (int y = 0; y < garden.size(); y++) {
            for (int x = 0; x < garden.getFirst().size(); x++) {
                if (!garden.get(y).get(x).isOrdered()) {
                    List<Box> region = new ArrayList<>();
                    populateRegion(garden, x, y, region);
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    private boolean isInsideGarden(List<List<Box>> garden, int x, int y) {
        return x >= 0 && y >= 0 && x < garden.getFirst().size() && y < garden.size();
    }

    private boolean isSamePlant(List<List<Box>> garden, int x, int y, char plant) {
        return isInsideGarden(garden, x, y) && garden.get(y).get(x).getPlant() == plant;
    }

    private void populateRegion(List<List<Box>> garden, int x, int y, List<Box> region) {
        Box current = garden.get(y).get(x);
        current.ordered();
        region.add(current);
        boolean isRightSamePlant = isSamePlant(garden, x + 1, y, current.getPlant());
        boolean isBottomSamePlant = isSamePlant(garden, x, y + 1, current.getPlant());
        boolean isLeftSamePlant = isSamePlant(garden, x - 1, y, current.getPlant());
        boolean isTopSamePlant = isSamePlant(garden, x, y - 1, current.getPlant());

        if (!isTopSamePlant) {
            current.addTopFence();
        }
        if (!isRightSamePlant) {
            current.addRightFence();
        }
        if (!isBottomSamePlant) {
            current.addBottomFence();
        }
        if (!isLeftSamePlant) {
            current.addLeftFence();
        }

        // Top right
        if (!isTopSamePlant && !isRightSamePlant ||
                isTopSamePlant && isRightSamePlant && !isSamePlant(garden, x + 1, y - 1, current.getPlant())) {
            current.addAngle();
        }

        // Bottom right
        if (!isBottomSamePlant && !isRightSamePlant ||
                isBottomSamePlant && isRightSamePlant && !isSamePlant(garden, x + 1, y + 1, current.getPlant())) {
            current.addAngle();
        }

        // Bottom left
        if (!isBottomSamePlant && !isLeftSamePlant ||
                isBottomSamePlant && isLeftSamePlant && !isSamePlant(garden, x - 1, y + 1, current.getPlant())) {
            current.addAngle();
        }

        // Top left
        if (!isTopSamePlant && !isLeftSamePlant ||
                isTopSamePlant && isLeftSamePlant && !isSamePlant(garden, x - 1, y - 1, current.getPlant())) {
            current.addAngle();
        }

        if (isRightSamePlant && !garden.get(y).get(x + 1).isOrdered()) {
            populateRegion(garden, x + 1, y, region);
        }
        if (isBottomSamePlant && !garden.get(y + 1).get(x).isOrdered()) {
            populateRegion(garden, x, y + 1, region);
        }
        if (isLeftSamePlant && !garden.get(y).get(x - 1).isOrdered()) {
            populateRegion(garden, x - 1, y, region);
        }
        if (isTopSamePlant && !garden.get(y - 1).get(x).isOrdered()) {
            populateRegion(garden, x, y - 1, region);
        }
    }

    private long calculateCost(List<List<Box>> regions) {
        return regions.stream().mapToLong(region ->
                region.stream().mapToLong(Box::getNumberOfFences).sum() *
                        region.size()
        ).sum();
    }

    private long calculateDiscountCost(List<List<Box>> regions) {
        return regions.stream().mapToLong(region ->
                region.stream().mapToLong(Box::getAngles).sum() *
                        region.size()
        ).sum();
    }

    @Override
    public String getFirstAnswer() {
        List<List<Box>> regions = groupByRegion(getInput());
        return Long.toString(calculateCost(regions));
    }

    @Override
    public String getSecondAnswer() {
        List<List<Box>> regions = groupByRegion(getInput());
        return Long.toString(calculateDiscountCost(regions));
    }
}

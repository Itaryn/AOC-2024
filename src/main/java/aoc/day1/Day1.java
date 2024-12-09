package aoc.day1;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day1 implements DailyExercise {

    @Override
    public String getFirstAnswer() {
        List<Integer>[] locationIdLists = readInput();
        int totalDistance = getTotalDistance(locationIdLists[0], locationIdLists[1]);
        return Integer.toString(totalDistance);
    }

    @Override
    public String getSecondAnswer() {
        List<Integer>[] locationIdLists = readInput();
        Map<Integer, Long> numberOfOccurences = getOccurences(locationIdLists[1]);
        long totalDistancesMultipliedByOccurences = getTotalDistanceMultipliedByOccurences(locationIdLists[0], numberOfOccurences);
        return Long.toString(totalDistancesMultipliedByOccurences);
    }

    private List<Integer>[] readInput() {
        List<Integer>[] locationIdLists = new List[]{new ArrayList<>(), new ArrayList<>()};

        try (BufferedReader br = new BufferedReader(new FileReader("./src/aoc/day1/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> splittedLine = Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList();
                locationIdLists[0].add(splittedLine.get(0));
                locationIdLists[1].add(splittedLine.get(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        locationIdLists[0].sort(Integer::compareTo);
        locationIdLists[1].sort(Integer::compareTo);

        return locationIdLists;
    }

    /**
     * The two list should be the same length and ordered
     *
     * @param list1 First list
     * @param list2 Second list
     * @return The sum of difference between each element of the lists
     */
    private int getTotalDistance(List<Integer> list1, List<Integer> list2) {
        return IntStream.range(0, list1.size())
                .mapToObj(i -> Math.abs(list1.get(i) - list2.get(i)))
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Map<Integer, Long> getOccurences(List<Integer> locationIdList) {
        return locationIdList.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }

    private long getTotalDistanceMultipliedByOccurences(List<Integer> locationIdList, Map<Integer, Long> numberOfOccurences) {
        return locationIdList.stream()
                .map(i -> i * numberOfOccurences.getOrDefault(i, 0L))
                .mapToLong(Long::longValue)
                .sum();
    }
}

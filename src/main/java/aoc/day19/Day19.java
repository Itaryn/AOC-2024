package aoc.day19;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 implements DailyExercise {
    Map<String, Long> knownCombinaisons = new HashMap<>(Map.of("", 1L));

    protected Input getInput(String path) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            List<String> patterns = Arrays.stream(reader.readLine().split(", ")).toList();
            // Skip empty line
            reader.readLine();
            String line = reader.readLine();
            List<String> designs = new ArrayList<>();
            while (line != null) {
                designs.add(line);
                line = reader.readLine();
            }
            return new Input(patterns, designs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<String> getPossibleDesigns(List<String> patterns, List<String> designs) {
        List<String> possibleDesigns = new ArrayList<>();
        Pattern regex = Pattern.compile("^((" + String.join("|", patterns) + "){1})+$");
        for (String design : designs) {
            if (regex.matcher(design).find()) {
                possibleDesigns.add(design);
            }
        }
        return possibleDesigns;
    }

    protected Map<String, Long> getNumberOfPossibleCombinaisons(List<String> patterns, List<String> designs) {
        return designs.stream()
                .map(design -> Map.entry(design, getNumberOfPossibleCombinaisons(design, patterns)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected long getNumberOfPossibleCombinaisons(String design, List<String> patterns) {
        if (knownCombinaisons.containsKey(design)) {
            return knownCombinaisons.get(design);
        }
        long combinaisons = patterns.stream().filter(design::startsWith)
                .mapToLong(pattern -> getNumberOfPossibleCombinaisons(design.substring(pattern.length()), patterns)).sum();
        knownCombinaisons.putIfAbsent(design, combinaisons);
        return combinaisons;
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput("/day19/input.txt");
        return Integer.toString(getPossibleDesigns(input.getPatterns(), input.getDesigns()).size());
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput("/day19/input.txt");
        return Long.toString(getNumberOfPossibleCombinaisons(input.getPatterns(), getPossibleDesigns(input.getPatterns(), input.getDesigns()))
                .values().stream().mapToLong(Long::longValue).sum());
    }
}

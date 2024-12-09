package aoc.day5;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 implements DailyExercise {
    @Override
    public String getFirstAnswer() {
        Input input = getInput();
        List<Print> goodPrint = input.getPrints().stream().filter(print -> isValidPrint(input, print)).toList();
        return Integer.toString(goodPrint.stream().mapToInt(this::getMiddleValue).sum());
    }

    private int getMiddleValue(Print print) {
        return print.getPages().get(print.getPages().size() / 2);
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput();
        List<Print> badPrint = input.getPrints().stream().filter(print -> !isValidPrint(input, print)).toList();
        List<Print> orderedPrints = badPrint.stream().map(print -> orderPrint(input, print)).toList();
        return Integer.toString(orderedPrints.stream().mapToInt(this::getMiddleValue).sum());
    }

    private Input getInput() {
        Input input = new Input();

        try (BufferedReader br = new BufferedReader(new FileReader("./src/aoc/day5/input.txt"))) {
            String line;
            boolean firstPart = true;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    firstPart = false;
                    continue;
                }
                if (firstPart) {
                    String[] splittedLine = line.split("\\|");
                    int beforePage = Integer.parseInt(splittedLine[0]);
                    int afterPage = Integer.parseInt(splittedLine[1]);
                    input.getPrintOrders().computeIfAbsent(beforePage, k -> new ArrayList<>());
                    input.getPrintOrders().get(beforePage).add(afterPage);
                } else {
                    String[] splittedLine = line.split(",");
                    input.addPrint(new Print()
                            .setPages(Arrays.stream(splittedLine).map(Integer::parseInt).toList()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private boolean isValidPrint(Input input, Print print) {
        boolean error = false;
        for (int i = 0; i < print.getPages().size(); i++) {
            int beforeValue = print.getPages().get(i);
            for (int j = i + 1; j < print.getPages().size(); j++) {
                if (input.getPrintOrders().containsKey(print.getPages().get(j)) &&
                        input.getPrintOrders().get(print.getPages().get(j)).contains(beforeValue)) {
                    error = true;
                }
            }
        }
        return !error;
    }

    private Print orderPrint(Input input, Print print) {
        List<Integer> values = new ArrayList<>(print.getPages());
        Print orderedPrint = new Print();
        for (int x = 0; x < print.getPages().size(); x++) {
            int valueOrdered = -1;
            int i = 0;
            while (valueOrdered == -1 && i < values.size()) {
                boolean error = false;
                int testValue = values.get(i);
                for (int j = 0; j < values.size(); j++) {
                    Integer afterValue = values.get(j);
                    if (testValue != afterValue &&
                            input.getPrintOrders().containsKey(afterValue) &&
                            input.getPrintOrders().get(afterValue).contains(testValue)) {
                        error = true;
                    }
                }
                if (!error) {
                    valueOrdered = testValue;
                } else {
                    i++;
                }
            }
            int finalValueOrdered = valueOrdered;
            values.removeIf(page -> page == finalValueOrdered);
            orderedPrint.getPages().add(valueOrdered);
        }
        return orderedPrint;
    }
}

package aoc.day5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Input {
    private final Map<Integer, List<Integer>> printOrders = new HashMap<>();
    private final List<Print> prints = new ArrayList<>();

    public Map<Integer, List<Integer>> getPrintOrders() {
        return printOrders;
    }

    public List<Print> getPrints() {
        return prints;
    }

    public void addPrint(Print print) {
        this.prints.add(print);
    }
}

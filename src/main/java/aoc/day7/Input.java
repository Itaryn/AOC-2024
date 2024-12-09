package aoc.day7;

import java.util.List;

public class Input {
    private final List<Calculation> calculationList;

    public Input(List<Calculation> calculationList) {
        this.calculationList = calculationList;
    }

    public List<Calculation> getCalculationList() {
        return calculationList;
    }
}

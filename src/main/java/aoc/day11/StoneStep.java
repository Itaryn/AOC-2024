package aoc.day11;

import java.util.List;

public class StoneStep {
    private final Long stone;
    private final int steps;
    private final List<Long> result;

    public StoneStep(Long stone, int steps, List<Long> result) {
        this.stone = stone;
        this.steps = steps;
        this.result = result;
    }

    public Long getStone() {
        return stone;
    }

    public int getSteps() {
        return steps;
    }

    public List<Long> getResult() {
        return result;
    }
}

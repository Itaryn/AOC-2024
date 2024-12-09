package aoc.day7;

import java.util.List;

public class Calculation {
    private final long result;
    private final List<Long> values;

    public Calculation(long result, List<Long> values) {
        this.result = result;
        this.values = values;
    }

    public long getResult() {
        return result;
    }

    public List<Long> getValues() {
        return values;
    }
}

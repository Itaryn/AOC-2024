package aoc.day19;

import java.util.List;

public class Input {
    private final List<String> patterns;
    private final List<String> designs;

    public Input(List<String> patterns, List<String> designs) {
        this.patterns = patterns;
        this.designs = designs;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public List<String> getDesigns() {
        return designs;
    }
}

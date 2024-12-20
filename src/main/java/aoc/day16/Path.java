package aoc.day16;

import java.util.List;

public class Path {
    private final long score;
    private final List<Position> path;

    public Path(long score, List<Position> path) {
        this.score = score;
        this.path = path;
    }

    public long getScore() {
        return score;
    }

    public List<Position> getPath() {
        return path;
    }
}

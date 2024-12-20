package aoc.day16;

public class Input {
    private final Reindeer reindeer;
    private final char[][] maze;

    public Input(Reindeer reindeer, char[][] maze) {
        this.reindeer = reindeer;
        this.maze = maze;
    }

    public Reindeer getReindeer() {
        return reindeer;
    }

    public char[][] getMaze() {
        return maze;
    }
}

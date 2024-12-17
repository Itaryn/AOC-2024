package aoc.day13;

public class Position {
    private long x;
    private long y;

    public Position(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public Position addToX(long x) {
        this.x += x;
        return this;
    }

    public long getY() {
        return y;
    }

    public Position addToY(long y) {
        this.y += y;
        return this;
    }
}

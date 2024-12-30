package aoc.day18;

public class PositionPath {
    private final Position position;
    private final long length;

    public PositionPath(Position position, long length) {
        this.position = position;
        this.length = length;
    }

    public Position getPosition() {
        return position;
    }

    public long getLength() {
        return length;
    }
}

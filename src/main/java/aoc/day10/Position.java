package aoc.day10;

import java.util.Objects;

public class Position {
    private final int endX;
    private final int endY;
    private int startX;
    private int startY;

    public Position(int endX, int endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public Position setStartX(int startX) {
        this.startX = startX;
        return this;
    }

    public Position setStartY(int startY) {
        this.startY = startY;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return startX == position.startX && startY == position.startY && endX == position.endX && endY == position.endY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startX, startY, endX, endY);
    }
}

package aoc.day16;

import java.util.Objects;

public class Reindeer {
    private Position position;
    private Direction direction;

    public Reindeer(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Reindeer setPosition(Position position) {
        this.position = position;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public Reindeer setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reindeer reindeer)) return false;
        return Objects.equals(position, reindeer.position) && direction == reindeer.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }
}

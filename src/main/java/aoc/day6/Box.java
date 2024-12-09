package aoc.day6;

import java.util.ArrayList;
import java.util.List;

public class Box implements Cloneable {
    private boolean obstacle;
    private boolean visited = false;
    private List<Character> visitedList = new ArrayList<>();

    public Box(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public boolean hasBeenVisited() {
        return visited;
    }

    public boolean hasBeenVisited(char direction) {
        return this.visitedList.contains(direction);
    }

    public void visit(char direction) {
        this.visited = true;
        this.visitedList.add(direction);
    }

    public boolean isAnObstacle() {
        return obstacle;
    }

    public void addObstacle() {
        this.obstacle = true;
    }

    public void removeObstacle() {
        this.obstacle = false;
    }

    @Override
    public String toString() {
        return isAnObstacle() ? "#" : hasBeenVisited() ? "X" : ".";
    }

    @Override
    public Box clone() {
        try {
            Box clone = (Box) super.clone();
            clone.visitedList = new ArrayList<>();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

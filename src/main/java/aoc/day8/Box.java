package aoc.day8;

public class Box {
    private final char value;
    private final int x;
    private final int y;
    private boolean antinode;

    public Box(char value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public char getValue() {
        return value;
    }

    public boolean isAntinode() {
        return antinode;
    }

    public void setAntinode() {
        this.antinode = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

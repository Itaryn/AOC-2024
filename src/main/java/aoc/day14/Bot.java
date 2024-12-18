package aoc.day14;

public class Bot {
    private final int moveX;
    private final int moveY;
    private int x;
    private int y;

    public Bot(int x, int y, int moveX, int moveY) {
        this.x = x;
        this.y = y;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public int getX() {
        return x;
    }

    public Bot setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Bot setY(int y) {
        this.y = y;
        return this;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }
}

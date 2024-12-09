package aoc.day6;

public class Guard implements Cloneable {
    private final int x;
    private final int y;
    private char direction;

    public Guard(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getDirection() {
        return direction;
    }

    public void moveRight() {
        switch (this.direction) {
            case '>':
                this.direction = 'v';
                break;
            case 'v':
                this.direction = '<';
                break;
            case '<':
                this.direction = '^';
                break;
            case '^':
                this.direction = '>';
                break;
            default:
                throw new RuntimeException("Direction is not set with a known char");
        }
    }

    @Override
    public Guard clone() {
        try {
            return (Guard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

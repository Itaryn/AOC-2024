package aoc.day9;

public class Block {
    private int size;
    private int index;

    public Block(int size, int index) {
        this.size = size;
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public Block setSize(int size) {
        this.size = size;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public Block setIndex(int index) {
        this.index = index;
        return this;
    }
}

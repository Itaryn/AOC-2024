package aoc.day3;

public class Mul {
    private int val1;
    private int val2;

    public Mul setVal1(int val1) {
        this.val1 = val1;
        return this;
    }

    public Mul setVal2(int val2) {
        this.val2 = val2;
        return this;
    }

    public int getResult() {
        return this.val1 * this.val2;
    }
}

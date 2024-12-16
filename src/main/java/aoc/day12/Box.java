package aoc.day12;

import java.util.stream.IntStream;

public class Box {
    private final char plant;
    // The first boolean is top and then is clockwise
    private final boolean[] fences = new boolean[4];
    private boolean ordered = false;
    private int angles = 0;

    public Box(char plant) {
        this.plant = plant;
    }

    public char getPlant() {
        return plant;
    }

    public void ordered() {
        this.ordered = true;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public int getNumberOfFences() {
        return (int) IntStream.range(0, 4).mapToObj(i -> fences[i]).filter(fence -> fence).count();
    }

    public void addTopFence() {
        this.fences[0] = true;
    }

    public void addRightFence() {
        this.fences[1] = true;
    }

    public void addBottomFence() {
        this.fences[2] = true;
    }

    public void addLeftFence() {
        this.fences[3] = true;
    }

    public int getAngles() {
        return angles;
    }

    public void addAngle() {
        this.angles++;
    }
}

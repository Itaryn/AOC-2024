package aoc.day15;

public class Input {
    private final Box[][] boxes;
    private final String instructions;

    public Input(Box[][] boxes, String instructions) {
        this.boxes = boxes;
        this.instructions = instructions;
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public String getInstructions() {
        return instructions;
    }
}

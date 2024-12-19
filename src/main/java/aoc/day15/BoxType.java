package aoc.day15;

import java.util.Arrays;

public enum BoxType {
    WALL('#'),
    BOX('O'),
    BOX_LEFT('['),
    BOX_RIGHT(']'),
    ROBOT('@'),
    EMPTY('.');

    private final char symbol;

    BoxType(char symbol) {
        this.symbol = symbol;
    }

    public static BoxType of(char symbol) {
        return Arrays.stream(BoxType.values())
                .filter(boxType -> boxType.symbol == symbol)
                .findFirst()
                .orElseThrow();
    }
}

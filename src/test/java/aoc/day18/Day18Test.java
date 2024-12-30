package aoc.day18;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {
    @Test
    void getInput_shouldReturnPositions_ifExample1() {
        List<Position> expected = List.of(
                new Position(5, 4),
                new Position(4, 2),
                new Position(4, 5),
                new Position(3, 0),
                new Position(2, 1),
                new Position(6, 3),
                new Position(2, 4),
                new Position(1, 5),
                new Position(0, 6),
                new Position(3, 3),
                new Position(2, 6),
                new Position(5, 1),
                new Position(1, 2),
                new Position(5, 5),
                new Position(2, 5),
                new Position(6, 5),
                new Position(1, 4),
                new Position(0, 4),
                new Position(6, 4),
                new Position(1, 1),
                new Position(6, 1),
                new Position(1, 0),
                new Position(0, 5),
                new Position(1, 6),
                new Position(2, 0)
        );
        assertArrayEquals(expected.toArray(), new Day18().getInput("/day18/example1.txt").toArray());
    }

    @Test
    void generateMap_shouldReturnMap_ifExample1() {
        List<List<Boolean>> expected = List.of(
                List.of(false, false, false, true, false, false, false),
                List.of(false, false, true, false, false, true, false),
                List.of(false, false, false, false, true, false, false),
                List.of(false, false, false, true, false, false, true),
                List.of(false, false, true, false, false, true, false),
                List.of(false, true, false, false, true, false, false),
                List.of(true, false, true, false, false, false, false)
        );
        assertArrayEquals(expected.toArray(), new Day18().generateMap(new Day18().getInput("/day18/example1.txt"), 12, 7).toArray());
    }

    @Test
    void foundShortestPath_shouldReturn22_ifExample1() {
        long expected = 22;
        assertEquals(expected, new Day18().foundShortestPath(new Day18().generateMap(new Day18().getInput("/day18/example1.txt"), 12, 7)));
    }
}

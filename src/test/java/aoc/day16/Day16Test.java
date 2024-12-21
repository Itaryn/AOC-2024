package aoc.day16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {

    @Test
    void getShortestPaths_shouldWork_if2PossiblePaths() {
        char[][] maze = new char[][] {
                {'#','#','#','#','#'},
                {'#','#','#','E','#'},
                {'#','.','.','.','#'},
                {'#','.','#','.','#'},
                {'#','.','.','.','#'},
                {'#','.','#','.','#'},
                {'#','#','#','#','#'},
        };
        Reindeer reindeer = new Reindeer(new Position(1, 5), Direction.RIGHT);
        List<Path> res = new Day16().getShortestPaths(maze, reindeer);
        assertEquals(3006, res.getFirst().getScore());
        assertEquals(10, res.stream().flatMap(path -> path.getPath().stream()).distinct().count());
    }

    @Test
    void getShortestPaths_shouldWorkd_ifExample1() {
        Input input = new Day16().getInput("/day16/example1.txt");
        List<Path> res = new Day16().getShortestPaths(input.getMaze(), input.getReindeer());
        assertEquals(7036, res.getFirst().getScore());
        assertEquals(45, res.stream().flatMap(path -> path.getPath().stream()).distinct().count());
    }
}
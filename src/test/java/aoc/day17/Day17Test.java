package aoc.day17;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class Day17Test {
    @Test
    void getInput_returnInput() {
        Input result = new Day17().getInput("/day17/example1.txt");
        assertEquals(3, result.getRegister().size());
        assertEquals(729, result.getRegister().get(0));
        assertEquals(0, result.getRegister().get(1));
        assertEquals(0, result.getRegister().get(2));
        assertLinesMatch(Stream.of("0", "1", "5", "4", "3", "0"), result.getProgram().stream().map(i -> Integer.toString(i)));
    }

    @Test
    void run_ShouldWork_ifExample1() {
        Input result = new Day17().getInput("/day17/example1.txt");
        assertLinesMatch(Stream.of("4", "6", "3", "5", "6", "3", "5", "2", "1", "0"),
                new Day17().run(result.getRegister(), result.getProgram()).stream().map(i -> Long.toString(i)));
    }

    @Test
    void getRegisterABruteForce_ShouldWork_ifExample2() {
        Input input = new Day17().getInput("/day17/example2.txt");
        assertEquals("117440", Long.toString(new Day17().getRegisterABruteForce(input.getRegister(), input.getProgram())));
    }
}
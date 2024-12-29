package aoc.day17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    public static Stream<Arguments> isValidWithForcedValuesExamples() {
        return Stream.of(
                Arguments.of(List.of(-1), 1, true),
                Arguments.of(List.of(1), 1, true),
                Arguments.of(List.of(0), 1, false),
                Arguments.of(List.of(0), 0, true),
                Arguments.of(List.of(1, 0, 0), 4, true),
                Arguments.of(List.of(1, 0, 1), 5, true),
                Arguments.of(List.of(1, 1, 0), 6, true),
                Arguments.of(List.of(1, 1, 1), 7, true),
                Arguments.of(List.of(0, 1, 1, 1), 7, true),
                Arguments.of(List.of(0, 1, 1, 1), 8, false)
        );
    }

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

    @Test
    void getPossibleValues_shouldThrow_ifDoesntEndWell() {
        List<Integer> program = List.of(0, 0, 1, 1, 2, 2, 3, 3, 4, 4);
        assertThrows(RuntimeException.class, () -> new Day17().getPossibleValues(program, 0));
    }

    @Test
    void getPossibleValues_shouldReturn() {
        List<Integer> program = List.of(2, 4, 1, 1, 7, 5, 1, 5, 4, 3, 5, 5, 0, 3, 3, 0);
        List<Integer> result = new Day17().getPossibleValues(program, 2);
        List<Integer> expected = List.of(6, 14, 22, 30, 34, 38, 46, 53, 54, 61, 62, 68, 70, 71, 76, 78, 79, 84, 86, 87, 92, 94, 95, 98, 102, 103, 110, 111, 118, 119, 126, 127, 162, 181, 189, 226, 290, 309, 317, 324, 332, 340, 348, 354, 418, 437, 445, 482, 546, 565, 573, 580, 583, 588, 591, 596, 599, 604, 607, 610, 615, 623, 631, 639, 674, 693, 701, 738, 802, 821, 829, 836, 844, 852, 860, 866, 930, 949, 957, 994);
        assertEquals(expected, result);
    }

    @Test
    void getPossibleValues_shouldWork() {
        Input input = new Day17().getInput("/day17/input.txt");
        List<Integer> expected = List.of(4);
        assertEquals(expected, new Day17().getPossibleValues(input.getProgram()).get(15));
    }

    @Test
    void getForcedValues_shouldWork() {
        Input input = new Day17().getInput("/day17/input.txt");
        Integer[] expected = new Integer[]{
                1, 0, 0, 1, 0, 1, -1, -1, -1, -1, -1, 0, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };
        assertArrayEquals(expected, new Day17().getForcedValues(input.getProgram(), new Day17().getPossibleValues(input.getProgram())));
    }

    @ParameterizedTest
    @MethodSource("isValidWithForcedValuesExamples")
    void isValidWithForcedValues_shouldWork(List<Integer> forcedValues, int valueToTest, boolean expected) {
        assertEquals(expected, new Day17().isValidWithForcedValues(forcedValues, valueToTest));
    }

    @Test
    void getMinRegisterA_shouldWork() {
        Input input = new Day17().getInput("/day17/input.txt");
        Long expected = 164278899142333L;
        assertEquals(expected, new Day17().getMinRegisterA(input.getProgram()));
    }
}
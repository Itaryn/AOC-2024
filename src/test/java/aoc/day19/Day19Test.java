package aoc.day19;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class Day19Test {

    public static Stream<Arguments> getCombinaisonsValues() {
        return Stream.of(
                Arguments.of("brwrr", 2),
                Arguments.of("bggr", 1),
                Arguments.of("gbbr", 4),
                Arguments.of("rrbgbr", 6),
                Arguments.of("ubwu", 0),
                Arguments.of("bwurrg", 1),
                Arguments.of("brgr", 2),
                Arguments.of("bbrgwb", 0)
        );
    }

    @Test
    void getInput_shouldWork_ifExample1() {
        List<String> expectedPatterns = List.of(
                "r", "wr", "b", "g", "bwu", "rb", "gb", "br"
        );
        List<String> expectedDesigns = List.of(
                "brwrr",
                "bggr",
                "gbbr",
                "rrbgbr",
                "ubwu",
                "bwurrg",
                "brgr",
                "bbrgwb"
        );
        Input result = new Day19().getInput("/day19/example1.txt");
        assertLinesMatch(expectedPatterns, result.getPatterns());
        assertLinesMatch(expectedDesigns, result.getDesigns());
    }

    @Test
    void getPossibleDesigns_shouldWork_ifExample1() {
        List<String> expectedDesigns = List.of(
                "brwrr",
                "bggr",
                "gbbr",
                "rrbgbr",
                "bwurrg",
                "brgr"
        );
        Input result = new Day19().getInput("/day19/example1.txt");
        assertLinesMatch(expectedDesigns, new Day19().getPossibleDesigns(result.getPatterns(), result.getDesigns()));
    }

    @ParameterizedTest
    @MethodSource("getCombinaisonsValues")
    void getNumberOfPossibleCombinaisons_shouldWork_ifExample1(String design, int expected) {
        Input result = new Day19().getInput("/day19/example1.txt");
        assertEquals(expected, new Day19().getNumberOfPossibleCombinaisons(design, result.getPatterns()));
    }
}
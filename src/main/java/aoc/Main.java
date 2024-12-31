package aoc;

import aoc.day19.Day19;

public class Main {
    public static void main(String[] args) {
        DailyExercise todayExercise = new Day19();

        System.out.printf("First answer of today is : %s%n", todayExercise.getFirstAnswer());
        System.out.printf("Second answer of today is : %s%n", todayExercise.getSecondAnswer());
    }
}
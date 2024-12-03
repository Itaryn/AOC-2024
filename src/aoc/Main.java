package aoc;

import aoc.day3.Day3;

public class Main {
    public static void main(String[] args) {
        DailyExercise todayExercise = new Day3();

        System.out.printf("First answer of today is : %s%n", todayExercise.getFirstAnswer());
        System.out.printf("Second answer of today is : %s%n", todayExercise.getSecondAnswer());
    }
}
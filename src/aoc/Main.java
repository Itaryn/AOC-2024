package aoc;

import aoc.day2.Day2;

public class Main {
    public static void main(String[] args) {
        DailyExercise todayExercise = new Day2();

        System.out.printf("First answer of today is : %s%n", todayExercise.getFirstAnswer());
        System.out.printf("Second answer of today is : %s%n", todayExercise.getSecondAnswer());
    }
}
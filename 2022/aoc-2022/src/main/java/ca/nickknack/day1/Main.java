package ca.nickknack.day1;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CalorieReader calorieReader = new CalorieReader();

        List<Elf> elves = calorieReader.readElfCaloriesFromFile("day1-input");

        System.out.println(String.format("Largest calorie count total: %s", CalorieCalculator.getLargestCaloricTotal(elves)));
        System.out.println(String.format("Sum of 3 largest calorie counts: %s", CalorieCalculator.getTopThreeCaloricTotal(elves)));
    }
}
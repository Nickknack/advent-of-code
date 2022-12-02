package ca.nickknack;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CalorieReader calorieReader = new CalorieReader();

        List<Elf> elves = calorieReader.readElfCaloriesFromFile("input");

        System.out.println(String.format("Largest calorie count total: %s", CalorieCalculator.getLargestCaloricTotal(elves)));
        System.out.println(String.format("Sum of 3 largest calorie counts: %s", CalorieCalculator.getTopThreeCaloricTotal(elves)));
    }
}
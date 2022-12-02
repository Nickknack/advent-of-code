package ca.nickknack;

import java.util.ArrayList;
import java.util.List;

public class Elf {
    private List<Integer> calorieEntries = new ArrayList<>();

    public void addCalorieEntry(Integer calorieEntry) {
        calorieEntries.add(calorieEntry);
    }

    public int getTotalCalories() {
        return calorieEntries.stream().reduce(0, Integer::sum);
    }
}
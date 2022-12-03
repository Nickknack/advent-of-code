package ca.nickknack.day1;

import java.util.Comparator;
import java.util.List;

public class CalorieCalculator {
    public static int getLargestCaloricTotal(List<Elf> elves) {
        return elves.stream()
                .mapToInt(elf -> elf.getTotalCalories())
                .max()
                .getAsInt();
    }

    public static int getTopThreeCaloricTotal(List<Elf> elves) {
        return elves.stream()
                .map(elf -> elf.getTotalCalories())
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(0, Integer::sum)
                .intValue();
    }
}

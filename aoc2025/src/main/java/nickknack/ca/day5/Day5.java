package nickknack.ca.day5;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Day5 {
    static void main() {
        List<Range> testRanges = new ArrayList<>();
        List<Long> testIngredients = new ArrayList<>();

        List<Range> actualRanges = new ArrayList<>();
        List<Long> actualIngredients = new ArrayList<>();


        CompletableFuture loadTestData = CompletableFuture.runAsync(() -> loadDataAndSort(FileReader.readFile(5, DayPart.TEST), testRanges, testIngredients));
        CompletableFuture loadRealData = CompletableFuture.runAsync(() -> loadDataAndSort(FileReader.readFile(5, DayPart.PART_1), actualRanges, actualIngredients));

        CompletableFuture.allOf(loadTestData, loadRealData).join();

        CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> getFreshCount(testRanges, testIngredients))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> part1 = CompletableFuture.supplyAsync(() -> getFreshCount(actualRanges, actualIngredients))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture.allOf(testPart1, part1).join();
    }

    private static void loadDataAndSort(List<String> lines, List<Range> ranges, List<Long> ingredients) {
        boolean loadingRanges = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                loadingRanges = false;
                continue;
            }

            if (loadingRanges) {
                String[] parts = line.split("-");
                ranges.add(new Range(Long.valueOf(parts[0]), Long.valueOf(parts[1])));
            } else {
                ingredients.add(Long.valueOf(line));
            }
        }

        Collections.sort(ingredients);
        ranges.sort(Comparator.comparing(Range::start));
    }

    private static long getFreshCount(List<Range> ranges, List<Long> ingredients) {
        List<Range> modifiedRange = new ArrayList<>(ranges);

        return ingredients.stream()
                .filter(ingredient -> isFresh(ingredient, ranges))
                .count();

    }

    private static boolean isFresh(Long ingredient, List<Range> ranges) {
        List<Range> updatedRange = new ArrayList<>(ranges);
        for (Range range : ranges) {
            if (ingredient >= range.start && ingredient <= range.end) {
                return true;
            } else if (ingredient > range.end) {
                updatedRange.remove(range);
            }
        }
        return false;
    }

    record Range(long start, long end) {}
}

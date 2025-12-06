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

        CompletableFuture<Void> test = CompletableFuture.supplyAsync(() -> getFreshCount(testRanges, testIngredients))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\nPart 2 Test: %s\n\n", result.freshIngredients, result.freshRangeCount));

        CompletableFuture<Void> actual = CompletableFuture.supplyAsync(() -> getFreshCount(actualRanges, actualIngredients))
                .thenAccept(result -> System.out.printf("Part 1: %s\nPart 2: %s\n\n", result.freshIngredients, result.freshRangeCount));

        CompletableFuture.allOf(test, actual).join();
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
        List<Range> mergedRanges = mergeOverlappingRanges(ranges);
        ranges.clear();
        ranges.addAll(mergedRanges);
    }

    private static Result getFreshCount(List<Range> ranges, List<Long> ingredients) {
        List<Range> modifiedRange = new ArrayList<>(ranges);

        long freshIngredientCount = ingredients.stream()
                .filter(ingredient -> isFresh(ingredient, ranges))
                .count();

        long freshRangeCount = ranges.stream()
                .map(range -> range.end - range.start + 1)
                .reduce(0L, Long::sum);

        return new Result(freshIngredientCount, freshRangeCount);
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

    private static List<Range> mergeOverlappingRanges(List<Range> ranges) {
        List<Range> condensedRanges = new ArrayList<>();
        Optional<Range> currentRange = Optional.empty();

        for (Range range : ranges) {
            if (currentRange.isEmpty()) {
                currentRange = Optional.of(range);
            } else if (range.start > currentRange.get().end) {
                condensedRanges.add(currentRange.get());
                currentRange = Optional.of(range);
            } else if (range.end > currentRange.get().end) {
                currentRange = Optional.of(new Range(currentRange.get().start, range.end));
            }
        }

        if (currentRange.isPresent()) {
            condensedRanges.add(currentRange.get());
        }

        return condensedRanges;
    }

    record Range(long start, long end) {}

    record Result(long freshIngredients, long freshRangeCount) {}
}

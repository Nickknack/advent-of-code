package nickknack.ca.day7;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Day7 {
    private static final char START = 'S';
    private static final char BLANK = '.';
    private static final char SPLITTER = '^';
    private static final char BEAM = '|';


    static void main() {

        List<String> testLines = FileReader.readFile(7, DayPart.TEST);
        List<String> actualLines = FileReader.readFile(7, DayPart.PART_1);

        CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> getSplitCount(testLines))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> actualPart1 = CompletableFuture.supplyAsync(() -> getSplitCount(actualLines))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture<Void> testPart2 = CompletableFuture.supplyAsync(() -> getPaths(testLines))
                .thenAccept(result -> System.out.printf("Part 2 Test: %s\n\n", result));

        CompletableFuture<Void> actualPart2 = CompletableFuture.supplyAsync(() -> getPaths(actualLines))
                .thenAccept(result -> System.out.printf("Part 2: %s\n\n", result));

        CompletableFuture.allOf(testPart1, actualPart1, testPart2, actualPart2).join();
    }

    private static long getPaths(List<String> lines) {
        Map<Integer, Long> beamCounts = new HashMap<>();

        int startIndex = lines.get(0).indexOf(START);
        beamCounts.put(startIndex, 1L);

        for (String line : lines) {
            Map<Integer, Long> newMap = new HashMap<>(beamCounts);
            for (Entry<Integer, Long> beam : beamCounts.entrySet()) {
                char spot = line.charAt(beam.getKey());
                if (spot == SPLITTER) {
                    newMap.remove(beam.getKey());
                    if (beam.getKey() + 1 < line.length()) {
                        newMap.put(beam.getKey() + 1, beam.getValue() + newMap.getOrDefault(beam.getKey() + 1, 0L));
                    }
                    if (beam.getKey() - 1 >= 0) {
                        newMap.put(beam.getKey() - 1, beam.getValue() + newMap.getOrDefault(beam.getKey() - 1, 0L));
                    }
                }
            }
            beamCounts = newMap;
        }

        return beamCounts.values()
                .stream()
                .reduce(0L, Long::sum);
    }

    private static long getSplitCount(List<String> lines) {
        long splitCount = 0L;

        char[][] grid = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] chars = line.toCharArray();

            for (int j = 0; j < chars.length; j++) {
                grid[i][j] = grid[i][j] == BEAM ? BEAM : chars[j];
                Optional<Character> above = i == 0 ? Optional.empty() : Optional.of(grid[i - 1][j]);

                if (!above.isEmpty()) {
                    if (grid[i][j] == BLANK) {
                        if (above.get() == BEAM) {
                            grid[i][j] = BEAM;
                        } else if (above.get() == START) {
                            grid[i][j] = BEAM;
                        } else {
                            grid[i][j] = BLANK;
                        }
                    } else if (grid[i][j] == SPLITTER) {
                        if (above.get() == BEAM) {
                            splitCount++;
                            grid[i][j - 1] = BEAM;
                            grid[i][j + 1] = BEAM;
                        }
                    }
                }
            }
        }

        return splitCount;
    }
}
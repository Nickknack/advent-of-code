package nickknack.ca.day8;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Day8 {
    static void main() {

        List<String> testLines = FileReader.readFile(8, DayPart.TEST);
        List<String> actualLines = FileReader.readFile(8, DayPart.PART_1);

        CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> circuitCalculation(testLines))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> actualPart1 = CompletableFuture.supplyAsync(() -> circuitCalculation(actualLines))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture.allOf(testPart1, actualPart1).join();
    }

    private static long circuitCalculation(List<String> lines) {
        return getCircuits(lines)
                .stream()
                .map(circuit -> Long.valueOf(circuit.size()))
                .sorted()
                .limit(3)
                .reduce(1L, (a, b) -> a * b);
    }

    private static List<Map<String, Boolean>> getCircuits(List<String> lines) {
        return new ArrayList<>();
    }
}
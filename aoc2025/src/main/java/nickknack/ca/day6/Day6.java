package nickknack.ca.day6;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Day6 {
    static void main() throws ExecutionException, InterruptedException {

        CompletableFuture<Stack<String>[]> loadTestData = CompletableFuture.supplyAsync(() -> getEquations(FileReader.readFile(6, DayPart.TEST)));
        CompletableFuture<Stack<String>[]> loadRealData = CompletableFuture.supplyAsync(() -> getEquations(FileReader.readFile(6, DayPart.PART_1)));

        CompletableFuture.allOf(loadTestData, loadRealData);

        Stack<String>[] testEquations = loadTestData.get();
        Stack<String>[] actualEquations = loadRealData.get();

        CompletableFuture<Void> test = CompletableFuture.supplyAsync(() -> getGrandTotal(testEquations))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> actual = CompletableFuture.supplyAsync(() -> getGrandTotal(actualEquations))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture.allOf(test, actual).join();
    }

    private static Stack<String>[] getEquations(List<String> lines) {
        Stack[] stacks = null;

        for (String line : lines) {
            String[] values = line.trim().split("\\s+");

            if (stacks == null) {
                stacks = new Stack[values.length];
            }

            for (int i = 0; i < values.length; i++) {
                if (stacks[i] == null) {
                    stacks[i] = new Stack<String>();
                }
                stacks[i].push(values[i]);
            }
        }

        return stacks;
    }

    private static long calculateEquation(Stack<String> equation) {
        Operation operation = Operation.fromSymbol(equation.pop());

        return equation.stream()
                .map(Long::valueOf)
                .reduce((val1, val2) -> Operation.ADD == operation ? val1 + val2 : val1 * val2)
                .orElse(0L);
    }

    private static long getGrandTotal(Stack<String>[] equations) {
        return Arrays.stream(equations)
                .map(equation -> calculateEquation(equation))
                .reduce(0L, Long::sum);
    }
}

package nickknack.ca.day6;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {
    static void main() throws ExecutionException, InterruptedException {

        List<String> testLines = FileReader.readFileByDelimeter(6, DayPart.TEST, "\\n");
        List<String> actualLines = FileReader.readFileByDelimeter(6, DayPart.PART_1, "\\n");

        CompletableFuture<Stack<String>[]> loadTestData = CompletableFuture.supplyAsync(() -> getEquations(testLines));
        CompletableFuture<Stack<String>[]> loadRealData = CompletableFuture.supplyAsync(() -> getEquations(actualLines));
        CompletableFuture<Stack<String>[]> loadTestColumnData = CompletableFuture.supplyAsync(() -> getColumnEquations(testLines));
        CompletableFuture<Stack<String>[]> loadRealColumnData = CompletableFuture.supplyAsync(() -> getColumnEquations(actualLines));

        CompletableFuture.allOf(loadTestData, loadRealData, loadTestColumnData, loadRealColumnData);

        Stack<String>[] testEquations = loadTestData.get();
        Stack<String>[] actualEquations = loadRealData.get();
        Stack<String>[] testColumnEquations = loadTestColumnData.get();
        Stack<String>[] actualColumnEquations = loadRealColumnData.get();

        CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> getGrandTotal(testEquations))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> actualPart1 = CompletableFuture.supplyAsync(() -> getGrandTotal(actualEquations))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture<Void> testPart2 = CompletableFuture.supplyAsync(() -> getGrandTotal(testColumnEquations))
                .thenAccept(result -> System.out.printf("Part 2 Test: %s\n\n", result));

        CompletableFuture<Void> actualPart2 = CompletableFuture.supplyAsync(() -> getGrandTotal(actualColumnEquations))
                .thenAccept(result -> System.out.printf("Part 2: %s\n\n", result));

        CompletableFuture.allOf(testPart1, actualPart1, testPart2, actualPart2).join();
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

    private static Stack<String>[] getColumnEquations(List<String> lines) {
        String[] operationString = getSubstringsMatchingPattern(lines.getLast(), "[*+]\\s*");

        String[][] grid = new String[lines.size() - 1][operationString.length];

        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            int currentIndex = 0;
            for (int j = 0; j < operationString.length; j++) {
                grid[i][j] = line.substring(currentIndex, currentIndex + operationString[j].length());
                currentIndex += operationString[j].length();
            }
        }

        Stack<String>[] stacks = new Stack[operationString.length];

        for (int i = 0; i < operationString.length; i++) {
            String[] numbers = new String[operationString[i].length()];
            Arrays.fill(numbers, "");

            stacks[i] = new Stack<>();

            for (int j = 0; j < grid.length; j++) {
                char[] digits = grid[j][i].toCharArray();
                for (int k = 0; k < digits.length; k++) {
                    numbers[k] += String.valueOf(digits[k]).trim();
                }
            }

            for (String number : numbers) {
                stacks[i].push(number);
            }
            stacks[i].push(operationString[i].trim());
        }

        return stacks;
    }

    private static String[] getSubstringsMatchingPattern(String string, String pattern) {
        Pattern operationPattern = Pattern.compile(pattern);
        Matcher operationStringMatcher = operationPattern.matcher(string);
        List<String> operationString = new ArrayList<>();

        while (operationStringMatcher.find()) {
            operationString.add(operationStringMatcher.group());
        }

        return operationString.toArray(new String[operationString.size()]);
    }

    private static long calculateEquation(Stack<String> equation) {
        Operation operation = Operation.fromSymbol(equation.pop());

        return equation.stream()
                .filter(num -> !num.isEmpty())
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
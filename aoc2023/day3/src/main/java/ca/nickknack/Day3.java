package ca.nickknack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("[^\\.\\d]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");
    private static final boolean IS_PART_TWO = true;

    public static void main(String[] args) {
        List<String> lines = new BufferedReader(new InputStreamReader(Day3.class.getClassLoader().getResourceAsStream("input.txt")))
                .lines()
                .toList();

        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
            List<String> comparingLines = new ArrayList<>();
            if (i < lines.size() - 1) {
                comparingLines.add(lines.get(i + 1));
            }

            if (i > 0) {
                comparingLines.add(lines.get(i - 1));
            }

            total += SumParts(lines.get(i), comparingLines, IS_PART_TWO);
        }

        System.out.println(total);
    }

    private static int SumParts(String evaluationLine, List<String> comparingLine, boolean isPartTwo) {
        Matcher matcher = SYMBOL_PATTERN.matcher(evaluationLine);
        int sum = 0;
        List<String> lineList = new ArrayList<>(comparingLine);
        lineList.add(evaluationLine);

        while (matcher.find()) {
            char symbol = matcher.group().charAt(0);
            sum += calculateAdjacentNumbers(lineList, symbol, matcher.start(), isPartTwo);
        }

        return sum;
    }

    private static int calculateAdjacentNumbers(List<String> linesToSearch, char symbol, int symbolIndex, boolean isPartTwo) {
        List<Integer> values = linesToSearch.stream()
                .flatMap(line -> findNumbersForLine(line, symbolIndex).stream())
                .toList();

        if (isPartTwo) {
            if (symbol == '*' && values.size() == 2) {
                return values.get(0) * values.get(1);
            }

            return 0;
        }

        return values.stream()
                .reduce(0, Integer::sum);
    }

    private static List<Integer> findNumbersForLine(String line, int symbolIndex) {
        List<Integer> integers = new ArrayList<>();
        Matcher matcher = DIGIT_PATTERN.matcher(line);
        while (matcher.find()) {
            int startIndex = matcher.start() == 0 ? 0 : matcher.start() - 1;
            if (symbolIndex >= startIndex && symbolIndex <= matcher.end()) {
                integers.add(Integer.valueOf(matcher.group()));
            }
        }

        return integers;
    }
}
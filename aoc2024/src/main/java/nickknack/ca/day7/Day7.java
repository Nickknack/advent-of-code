package nickknack.ca.day7;

import java.util.*;

public class Day7 {
    private static final String TEST_INPUT = "test/day7/input.txt";
    private static final String INPUT = "actual/day7/input.txt";

    public static void main(String[] args) {
        Map<Long, List<Long>> input = readInput(INPUT);

        part1(input);
    }

    public static void part1(Map<Long, List<Long>> input) {
        System.out.println("Part 1 start");

        Long result = input.entrySet().stream()
                .filter(entry -> isValidEquation(entry.getKey(), entry.getValue()))
                .map(Map.Entry::getKey)
                .reduce(0L, Long::sum);

        System.out.printf("result of Part 1 is: %s\n", result);
    }

    private static Map<Long, List<Long>> readInput(String fileLocation) {
        Map<Long, List<Long>> input = new HashMap<>();
        try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(":");
                Long testValue = Long.parseLong(line[0]);

                List<Long> values = Arrays.stream(line[1].trim().split(" "))
                        .map(Long::parseLong)
                        .toList();

                input.put(testValue, values);
            }
        }

        return input;
    }

    private static boolean isValidEquation(long expected, List<Long> values) {
        return validateSublist(expected, values.getFirst(), values.subList(1, values.size()));
    }

    private static boolean validateSublist(long expected, long total, List<Long> remainingValues) {
        if (remainingValues.isEmpty()) {
            return expected == total;
        }

        if (validateSublist(expected, total * remainingValues.getFirst(), remainingValues.subList(1, remainingValues.size()))) {
            return true;
        }

        if (validateSublist(expected, total + remainingValues.getFirst(), remainingValues.subList(1, remainingValues.size()))) {
            return true;
        }

        return false;
    }
}

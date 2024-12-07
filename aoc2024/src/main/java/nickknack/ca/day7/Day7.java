package nickknack.ca.day7;

import java.util.*;

public class Day7 {
    private static final String TEST_INPUT = "test/day7/input.txt";
    private static final String INPUT = "actual/day7/input.txt";
    private static final boolean TEST = false;

    public static void main(String[] args) {
        Map<Long, List<Long>> input = readInput(TEST ? TEST_INPUT : INPUT);

        part1(input);
        part2(input);
    }

    public static void part1(Map<Long, List<Long>> input) {
        System.out.println("Part 1 start");
        Long result = doExercise(input, List.of(Operator.ADD, Operator.MULTIPLY));
        System.out.printf("result of Part 1 is: %s\n", result);
    }

    public static void part2(Map<Long, List<Long>> input) {
        System.out.println("Part 2 start");
        Long result = doExercise(input, List.of(Operator.ADD, Operator.MULTIPLY, Operator.CONCATENATE));
        System.out.printf("result of Part 2 is: %s\n", result);
    }

    public static long doExercise(Map<Long, List<Long>> input, List<Operator> validOperators) {
        return input.entrySet().stream()
                .filter(entry -> isValidEquation(entry.getKey(), entry.getValue(), validOperators))
                .map(Map.Entry::getKey)
                .reduce(0L, Long::sum);
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

    private static boolean isValidEquation(long expected, List<Long> values, List<Operator> validOperators) {
        return validateSublist(expected, values.getFirst(), values.subList(1, values.size()), validOperators);
    }

    private static boolean validateSublist(long expected, long total, List<Long> remainingValues, List<Operator> validOperators) {
        if (remainingValues.isEmpty()) {
            return expected == total;
        }

        if (validOperators.contains(Operator.ADD) && validateSublist(expected, total * remainingValues.getFirst(), remainingValues.subList(1, remainingValues.size()), validOperators)) {
            return true;
        }

        if (validOperators.contains(Operator.MULTIPLY) && validateSublist(expected, total + remainingValues.getFirst(), remainingValues.subList(1, remainingValues.size()), validOperators)) {
            return true;
        }

        if (validOperators.contains(Operator.CONCATENATE) && validateSublist(expected, Long.parseLong("%s%s".formatted(total, remainingValues.getFirst())), remainingValues.subList(1, remainingValues.size()), validOperators)) {
            return true;
        }

        return false;
    }
}

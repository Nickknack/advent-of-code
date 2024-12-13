package nickknack.ca.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Day11 {
	private static final String TEST_INPUT = "test/day11/input.txt";
	private static final String INPUT = "actual/day11/input.txt";

	private static final boolean TEST = false;

	private static final int PART_1_BLINKS = 25;
	private static final int PART_2_BLINKS = 75;

	public static void main(String[] args) {
		Map<String, Long> initialStones = readInput(TEST ? TEST_INPUT : INPUT);

		part1(initialStones, PART_1_BLINKS);
		part2(initialStones, PART_2_BLINKS);
	}

	private static void part1(Map<String, Long> stones, int blinks) {
		System.out.println("Part 1 start");

		for (int i = 0; i < blinks; i++) {
			stones = blink(stones);
		}

		long stoneCount = stones.values().stream()
				.reduce(0L, Long::sum);

		System.out.printf("Part 1 results: %s\n", stoneCount);
	}

	private static void part2(Map<String, Long> stones, int blinks) {
		System.out.println("Part 2 start");

		for (int i = 0; i < blinks; i++) {
			stones = blink(stones);
		}

		long stoneCount = stones.values().stream()
				.reduce(0L, Long::sum);

		System.out.printf("Part 2 results: %s\n", stoneCount);
	}

	private static Map<String, Long> blink(Map<String, Long> before) {
		Map<String, Long> stones = new HashMap<>();

		before.forEach((k, v) -> {
			if (k.equals("0")) {
				stones.put("1", stones.getOrDefault("1", 0L) + v);
			} else if (k.length() % 2 == 0) {
				String leftStone = removeLeadingZeros(k.substring(0, k.length() / 2));
				stones.put(leftStone, stones.getOrDefault(leftStone, 0L) + v);
				String rightStone = removeLeadingZeros(k.substring(k.length() / 2));
				stones.put(rightStone, stones.getOrDefault(rightStone, 0L) + v);
			} else {
				String newKey = String.valueOf(Long.parseLong(k) * 2024L);
				stones.put(newKey, stones.getOrDefault(newKey, 0L) + v);
			}
		});

		return stones;
	}

	private static String removeLeadingZeros(String value) {
		return String.valueOf(Long.parseLong(value));
	}

	private static Map<String, Long> readInput(String fileLocation) {
		Map<String, Long> stones = new HashMap<>();

		try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
			Arrays.stream(scanner.nextLine().split(" "))
					.forEach(value -> stones.put(value, stones.getOrDefault(value, 0L) + 1L));
		}

		return stones;
	}
}

package nickknack.ca.day2;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day2 {
	private static final String DELIMITER = ",";

	static void main() {
		List<String> testData = FileReader.readFileByDelimeter(2, DayPart.TEST, DELIMITER);
		List<String> realData = FileReader.readFileByDelimeter(2, DayPart.PART_1, DELIMITER);

		CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> part1(testData))
				.thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

		CompletableFuture<Void> testPart2 = CompletableFuture.supplyAsync(() -> part2(testData))
				.thenAccept(result -> System.out.printf("Part 2 Test: %s\n\n", result));

		CompletableFuture<Void> part1 = CompletableFuture.supplyAsync(() -> part1(realData))
				.thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

		CompletableFuture<Void> part2 = CompletableFuture.supplyAsync(() -> part2(realData))
				.thenAccept(result -> System.out.printf("Part 2: %s\n\n", result));

		CompletableFuture.allOf(testPart1, part1, testPart2, part2).join();
	}

	private static long part1(List<String> data) {
		return data.stream()
				.flatMap(line -> getInvalidIdsPart1(line).stream())
				.reduce(0L, Long::sum);
	}

	private static long part2(List<String> data) {
		return data.stream()
				.flatMap(line -> getInvalidIdsPart2(line).stream())
				.reduce(0L, Long::sum);
	}

	private static List<Long> getInvalidIdsPart2(String range) {
		String[] split = range.trim().split("-");
		String start = split[0];
		String end = split[1];
		List<Long> invalidIds = new ArrayList<>();

		String current = start;

		while (Long.valueOf(current) <= Long.valueOf(end)) {
			for (int i = 0; i < current.length() / 2; i++) {
				String substring = current.substring(0, i + 1);
				if (current.length() % substring.length() == 0) {
					String repeatedSubstring = substring.repeat(current.length() / substring.length());
					if (repeatedSubstring.equals(current)) {
						invalidIds.add(Long.valueOf(current));
						break;
					}
				}
			}
			current = String.valueOf(Long.valueOf(current) + 1L);
		}

		return invalidIds;
	}

	private static List<Long> getInvalidIdsPart1(String range) {
		String[] split = range.trim().split("-");
		String start = split[0];
		String end = split[1];
		List<Long> invalidIds = new ArrayList<>();

		String current = start;
		while (Long.valueOf(current) <= Long.valueOf(end)) {
			if (current.length() % 2 == 0) {
				String left = current.substring(0, current.length() / 2);
				long potentialId = Long.valueOf(left + left);

				if (potentialId <= Long.valueOf(end) && potentialId >= Long.valueOf(start)) {
					invalidIds.add(potentialId);
				}

				current = String.valueOf(Long.valueOf(left) + 1L).repeat(2);
			} else {
				current = String.valueOf(Long.valueOf(current) + 1L);
			}
		}

		return invalidIds;
	}
}

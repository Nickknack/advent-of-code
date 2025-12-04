package nickknack.ca.day4;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day4 {
	private static final char BLANK = '.';
	private static final char ROLL = '@';
	private static final int THRESHOLD = 4;

	static void main() {
		char[][] testMap = createMap(FileReader.readFile(4, DayPart.TEST));
		char[][] realMap = createMap(FileReader.readFile(4, DayPart.PART_1));

		CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> checkMap(testMap, false))
				.thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));
		CompletableFuture<Void> part1 = CompletableFuture.supplyAsync(() -> checkMap(realMap, false))
				.thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));
		CompletableFuture<Void> testPart2 = CompletableFuture.supplyAsync(() -> checkMap(testMap, true))
				.thenAccept(result -> System.out.printf("Part 2 Test: %s\n\n", result));
		CompletableFuture<Void> part2 = CompletableFuture.supplyAsync(() -> checkMap(realMap, true))
				.thenAccept(result -> System.out.printf("Part 2: %s\n\n", result));

		CompletableFuture.allOf(testPart1, part1, testPart2, part2).join();
	}

	private static char[][] createMap(List<String> lines) {
		return lines.stream()
				.map(line -> line.toCharArray())
				.toList().toArray(new char[0][]);
	}

	private static int checkMap(char[][] map, boolean removeRolls) {
		char[][] myMap = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);

		int accessibleRolls = 0;
		boolean runAgain = true;

		while(runAgain) {
			runAgain = false;
			for (int row = 0; row < myMap.length; row++) {
				for (int col = 0; col < myMap[row].length; col++) {
					if (myMap[row][col] == ROLL) {
						if (checkAdjacents(myMap, row, col, THRESHOLD)) {
							if (removeRolls) {
								myMap[row][col] = BLANK;
								runAgain = true;
							}
							accessibleRolls++;
						}
					}
				}
			}
		}

		return accessibleRolls;
	}

	private static boolean checkAdjacents(char[][] map, int row, int col, int threshold) {
		int hits = 0;
		for (Direction direction : Direction.values()) {
			if (checkPosition(map, row + direction.getRow(), col + direction.getCol())) {
				hits++;
				if (hits == threshold) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean checkPosition(char[][] map, int row, int col) {
		if (row < 0 || row >= map.length) {
			return false;
		}

		if (col < 0 || col >= map[row].length) {
			return false;
		}

		return map[row][col] == ROLL;
	}
}

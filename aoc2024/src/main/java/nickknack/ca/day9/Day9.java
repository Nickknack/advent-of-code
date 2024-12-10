package nickknack.ca.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day9 {
	private static final String TEST_INPUT = "test/day9/input.txt";
	private static final String INPUT = "actual/day9/input.txt";

	private static final boolean TEST = false;

	public static void main(String[] args) {
		String input = readInput(TEST ? TEST_INPUT : INPUT);

		File[] files = parseInput(input);

		part1(files);
	}

	private static void part1(File[] files) {
		System.out.println("Part 1 start");
		compact(files);

		long checksum = calculateChecksum(files);

		System.out.printf("Part 1 result: %s\n", checksum);
	}

	private static String readInput(String fileLocation) {
		try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
			return scanner.nextLine();
		}
	}

	private static File[] parseInput(String input) {
		List<File> files = new ArrayList<>();
		long id = 0L;
		for (int i = 0; i < input.length(); i += 2) {
			int block = Integer.parseInt(String.valueOf(input.charAt(i)));
			int freeSpace = i + 1 == input.length() ? 0 : Integer.parseInt(String.valueOf(input.charAt(i + 1)));

			files.add(new File(id, block, freeSpace));
			id++;
		}

		return files.toArray(File[]::new);
	}

	private static void compact(File[] files) {
		int rightmostNonEmptyIndex = findRightmostNonEmpty(files, files.length - 1);
		for (int i = 0; i < files.length && i < rightmostNonEmptyIndex; i++) {
			File nonFullFile = files[i];
			while (nonFullFile.hasFreeSpace()) {
				if (files[rightmostNonEmptyIndex].isEmpty()) {
					rightmostNonEmptyIndex = findRightmostNonEmpty(files, rightmostNonEmptyIndex);
				}

				nonFullFile.addValueToSpace(files[rightmostNonEmptyIndex].popValue());
			}
		}
	}

	private static int findRightmostNonEmpty(File[] files, int startingIndex) {
		for (int i = startingIndex; i >= 0; i--) {
			if (!files[i].isEmpty()) {
				return i;
			}
		}

		throw new RuntimeException("Only empty space to left of index %s".formatted(startingIndex));
	}

	private static long calculateChecksum(File[] files) {
		long hashPosition = 0;
		long hashTotal = 0;
		for (File file : files) {
			for (long block : file.getBlocks()) {
				hashTotal += block * hashPosition;
				hashPosition++;
			}
		}

		return hashTotal;
	}
}

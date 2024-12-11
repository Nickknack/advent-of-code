package nickknack.ca.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Day9 {
	private static final String TEST_INPUT = "test/day9/input.txt";
	private static final String INPUT = "actual/day9/input.txt";

	private static final boolean TEST = false;

	private static final long EMPTY_SPACE = -1L;

	public static void main(String[] args) {
		String input = readInput(TEST ? TEST_INPUT : INPUT);

		File[] files = parseInput(input);

		part1(files);
		part2(parseInputAsArray(input));
	}

	private static void part1(File[] files) {
		System.out.println("Part 1 start");
		compact(files);

		long checksum = calculateChecksum(files);

		System.out.printf("Part 1 result: %s\n", checksum);
	}

	private static void part2(long[] input) {
		System.out.println("Part 2 start");
		compactWholeFiles(input);

		long checksum = calculateChecksumOfArray(input);

		System.out.printf("Part 2 result: %s\n", checksum);
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

	private static long[] parseInputAsArray(String input) {
		long id = 0L;
		List<Long> result = new ArrayList<>();
		for (int i = 0; i < input.length(); i += 2) {
			int block = Integer.parseInt(String.valueOf(input.charAt(i)));
			int freeSpace = i + 1 == input.length() ? 0 : Integer.parseInt(String.valueOf(input.charAt(i + 1)));

			for (int j = 0; j < block + freeSpace; j++) {
				if (j < block) {
					result.add(id);
				} else {
					result.add(EMPTY_SPACE);
				}
			}
			id++;
		}
		return result.stream().mapToLong(l -> l)
				.toArray();

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

	private static void compactWholeFiles(long[] input) {
		int rightmostIndex = getRightmostBlockIndex(input, input.length - 1);
		Optional<Integer> fileStartIndex = getFileStartIndex(input, rightmostIndex);

		while (fileStartIndex.isPresent() && fileStartIndex.get() > 0) {
			int spaceNeeded = rightmostIndex - fileStartIndex.get() + 1;
			Optional<Integer> freeSpaceIndex = getIndexOfSectionWithEnoughSpace(input, spaceNeeded, fileStartIndex.get());
			if (freeSpaceIndex.isPresent()) {
				long id = input[rightmostIndex];
				for (int i = 0; i < spaceNeeded; i++) {
					input[freeSpaceIndex.get() + i] = id;
					input[fileStartIndex.get() + i] = EMPTY_SPACE;
				}
			}

			rightmostIndex = getRightmostBlockIndex(input, fileStartIndex.get() - 1);
			fileStartIndex = getFileStartIndex(input, rightmostIndex);

		}
	}

	private static int getRightmostBlockIndex(long[] input, int indexStart) {
		for (int i = indexStart; i > 0; i--) {
			if (input[i] != EMPTY_SPACE) {
				return i;
			}
		}

		return 0;
	}

	private static Optional<Integer> getIndexOfSectionWithEnoughSpace(long[] input, int spaceNeeded, int endIndex) {
		int continuousFreeSpace = 0;
		for (int i = 0; i <= endIndex; i++) {
			if (input[i] == EMPTY_SPACE) {
				continuousFreeSpace++;
				if (continuousFreeSpace >= spaceNeeded) {
					return Optional.of(i + 1 - spaceNeeded);
				}
			} else {
				continuousFreeSpace = 0;
			}
		}
		return Optional.empty();
	}

	private static Optional<Integer> getFileStartIndex(long[] input, int fileEndIndex) {
		long id = input[fileEndIndex];
		int fileStartIndex = fileEndIndex;
		while (id == input[fileStartIndex]) {
			fileStartIndex--;
			if (fileStartIndex < 0) {
				return Optional.empty();
			}
		}

		return Optional.of(++fileStartIndex);
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

	private static long calculateChecksumOfArray(long[] blocks) {
		long hashTotal = 0L;

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != EMPTY_SPACE) {
				hashTotal += blocks[i] * i;
			}
		}

		return hashTotal;
	}
}

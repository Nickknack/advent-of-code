package nickknack.ca.day9;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

public class Day9 {
	private static final String TEST_INPUT = "test/day9/input.txt";
	private static final String INPUT = "actual/day9/input.txt";

	private static final boolean TEST = false;

	private static final char FREE_SPACE = '.';

	public static void main(String[] args) {
		String input = readInput(TEST ? TEST_INPUT : INPUT);

		System.out.println(parseInput(input));
//		System.out.println(compact(parseInput(input)));

		BigDecimal checksum = calculateChecksum(compact(parseInput(input)));

		System.out.println(checksum);
	}

	private static String readInput(String fileLocation) {
		try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
			return scanner.nextLine();
		}
	}

	private static String parseInput(String input) {
		StringBuilder result = new StringBuilder();
		long id = 0L;
		for (int i = 0; i < input.length(); i += 2) {
			int block = Integer.parseInt(String.valueOf(input.charAt(i)));
			int freeSpace = i + 1 == input.length() ? 0 : Integer.parseInt(String.valueOf(input.charAt(i + 1)));

			result.append(String.valueOf(id).repeat(block));
			result.append(String.valueOf(FREE_SPACE).repeat(freeSpace));
			id++;
		}

		return result.toString();
	}

	private static String compact(String parsedInput) {
		char[] inputArray = parsedInput.toCharArray();
		int rightmostNonEmptyIndex = parsedInput.length() - 1;
		for (int i = 0; i < inputArray.length && i < rightmostNonEmptyIndex; i++) {
			if (inputArray[i] == FREE_SPACE) {
				rightmostNonEmptyIndex = findRightmostNonEmpty(inputArray, rightmostNonEmptyIndex);
				inputArray[i] = inputArray[rightmostNonEmptyIndex];
				inputArray[rightmostNonEmptyIndex] = FREE_SPACE;
			}
		}

		return String.valueOf(inputArray).replaceAll("\\.", "");
	}

	private static int findRightmostNonEmpty(char[] characters, int startingIndex) {
		for (int i = startingIndex; i >= 0; i--) {
			if (characters[i] != FREE_SPACE) {
				return i;
			}
		}

		throw new RuntimeException("Only empty space to left of index %s".formatted(startingIndex));
	}

	private static BigDecimal calculateChecksum(String compactString) {
		BigDecimal checksum = BigDecimal.ZERO;
		for (int i = 0; i < compactString.length(); i++) {
			checksum = checksum.add(BigDecimal.valueOf(i * Long.parseLong(String.valueOf(compactString.charAt(i)))));
		}

		return checksum;
	}
}

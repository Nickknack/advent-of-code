package nickknack.ca.day4;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
	private static final String TARGET = "XMAS";

	public static void main(String[] args) {
		char[][] puzzle = loadPuzzle();

		int totalWords = 0;

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j] == TARGET.charAt(0)) {
					totalWords += getHits(puzzle, i, j);
				}
			}
		}
		System.out.println(totalWords);
	}

	private static int getHits(char[][] puzzle, int row, int col) {
		int hits = 0;
		for (Direction direction: Direction.values()) {
			if (testDirection(direction, row, col, puzzle)) {
				hits++;
			};
		}

		return hits;
	}

	private static boolean testDirection(Direction direction, int row, int col, char[][] puzzle) {
		if (!isCheckValid(row, puzzle.length, direction.rowModifier)) {
			return false;
		}

		if (!isCheckValid(col, puzzle[row].length, direction.columnModifier)) {
			return false;
		}

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < TARGET.length(); i++) {
			result.append(puzzle[row + i * direction.rowModifier][col + i * direction.columnModifier]);
		}

		return TARGET.contentEquals(result);
	}

	private static boolean isCheckValid(int currentPosition, int maxLength, int directionModifier) {
		int movementNeeded = currentPosition + (TARGET.length() - 1) * directionModifier;
		return movementNeeded < maxLength && movementNeeded >= 0;
	}

	private static char[][] loadPuzzle() {
		List<String> lines = new ArrayList<>();
		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("test/day4/input.txt"))) {
			while (scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}
		}

		return lines.stream().map(String::toCharArray).toArray(char[][]::new);
	}

	enum Direction {
		UP(-1, 0),
		DOWN(1, 0),
		LEFT(0, -1),
		RIGHT(0, 1),
		DOWN_LEFT(1, -1),
		DOWN_RIGHT(1, 1),
		UP_LEFT(-1, -1),
		UP_RIGHT(-1, 1);

		final int rowModifier;
		final int columnModifier;

		Direction(int rowModifier, int columnModifier) {
			this.rowModifier = rowModifier;
			this.columnModifier = columnModifier;
		}
	}
}

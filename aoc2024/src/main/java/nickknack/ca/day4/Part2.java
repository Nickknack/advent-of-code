package nickknack.ca.day4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part2 {
	private static final String TARGET = "MAS";

	public static void main(String[] args) {
		char[][] puzzle = loadPuzzle();

		int totalX = 0;

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j] == TARGET.charAt(1)) {
					if (isHit(i, j, puzzle)) {
						totalX++;
					}
				}
			}
		}
		System.out.println(totalX);
	}

	private static boolean isHit(int row, int col, char[][] puzzle) {
		if (row - 1 < 0 || row + 1 >= puzzle.length) {
			return false;
		}

		if (col - 1 < 0 || col + 1 >= puzzle[row].length) {
			return false;
		}


		if (!List.of(puzzle[row - 1][col - 1], puzzle[row + 1][col + 1])
				.containsAll(List.of('M', 'S'))) {
			return false;
		};

		if (!List.of(puzzle[row - 1][col + 1], puzzle[row + 1][col - 1])
				.containsAll(List.of('M', 'S'))) {
			return false;
		};

		return true;
	}

	private static char[][] loadPuzzle() {
		List<String> lines = new ArrayList<>();
		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("actual/day4/input.txt"))) {
			while (scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}
		}

		return lines.stream().map(String::toCharArray).toArray(char[][]::new);
	}
}

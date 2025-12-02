package nickknack.ca.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Day12 {
	private static final String TEST_INPUT = "test/day12/input.txt";
	private static final String INPUT = "actual/day12/input.txt";

	private static final boolean TEST = true;

	public static void main(String[] args) {
		Plot[][] garden = readInput(TEST ? TEST_INPUT : INPUT);
		Map<Integer, List<Plot>> regions = getRegions(garden);

		part1(regions);
	}

	private static void part1(Map<Integer, List<Plot>> regions) {
		System.out.println("Part 1 start");

		long result = regions.values().stream()
				.map(Day12::calculateAreaCost)
				.reduce(0L, Long::sum);

		System.out.printf("Part 1 results: %s\n", result);
	}

	private static long calculateAreaCost(List<Plot> plots) {
		int total = plots.stream()
				.map(Plot::getFences)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(0, Integer::sum);
		return (long) total * plots.size();
	}

	private static Map<Integer, List<Plot>> getRegions(Plot[][] garden) {
		int areaId = 0;
		char areaType;
		Map<Integer, List<Plot>> areas = new HashMap<>();

		for (int i = 0; i < garden.length; i++) {
			for (int j = 0; j < garden[0].length; j++) {
				setFences(garden, i, j);
				if (!garden[i][j].isInArea()) {
					areaType = garden[i][j].getType();
					areas.put(areaId, getPlotsForArea(areaType, i, j, garden));
					areaId++;
				}
			}
		}

		return areas;
	}

	private static List<Plot> getPlotsForArea(char areaType, int row, int col, Plot[][] garden) {
		List<Plot> plots = new ArrayList<>();
		if (!isValidCoordinate(row, col, garden.length - 1, garden[0].length - 1) || garden[row][col].isInArea()) {
			return new ArrayList<>();
		}

		if (garden[row][col].getType() == areaType) {
			garden[row][col].addToArea();
			plots.add(garden[row][col]);

			for (Direction direction : Direction.values()) {
				plots.addAll(getPlotsForArea(areaType, row + direction.getRowMover(), col + direction.getColumnMover(), garden));
			}
		}

		return plots;
	}

	private static void setFences(Plot[][] garden, int row, int col) {
		List<Direction> directionsWithFences = new ArrayList<>();
		for (Direction direction : Direction.values()) {
			int newRow = row + direction.getRowMover();
			int newCol = col + direction.getColumnMover();
			if (!isValidCoordinate(newRow, newCol, garden.length - 1, garden[0].length - 1)) {
				directionsWithFences.add(direction);
			} else if (garden[row][col].getType() != garden[newRow][newCol].getType()) {
				directionsWithFences.add(direction);
			}
		}

		garden[row][col].setFences(directionsWithFences.size());
	}

	private static Plot[][] readInput(String fileLocation) {
		List<Plot[]> rows = new ArrayList<>();
		try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
			while (scanner.hasNext()) {
				rows.add(scanner.nextLine().chars().mapToObj(charInt -> new Plot((char) charInt)).toArray(Plot[]::new));
			}
		}

		return rows.toArray(Plot[][]::new);
	}

	private static boolean isValidCoordinate(int row, int col, int maxRow, int maxCol) {
		return row >= 0 && col >= 0 && row <= maxRow && col <= maxCol;
	}
}

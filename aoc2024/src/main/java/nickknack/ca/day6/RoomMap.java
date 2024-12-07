package nickknack.ca.day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class RoomMap {
	private static final char OBSTACLE_SYMBOL = '#';
	private static final char OPEN_SPACE = '.';
	private static final List<Character> VALID_MAP_SPACES = List.of(OBSTACLE_SYMBOL, OPEN_SPACE);

	private final char[][] map;
	private Guard guard;

	RoomMap(String fileLocation) {
		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation))) {
			List<char[]> map = new ArrayList<>();
			int rowNum = 0;
			while (scanner.hasNext()) {
				char[] rowValues = scanner.nextLine().toCharArray();
				char[] newRow = new char[rowValues.length];
				for (int i = 0; i < rowValues.length; i++) {
					char squareValue = rowValues[i];
					if (VALID_MAP_SPACES.contains(squareValue)) {
						newRow[i] = squareValue;
					} else {
						guard = new Guard(rowNum, i, squareValue);
						newRow[i] = OPEN_SPACE;
					}
				}
				map.add(newRow);
				rowNum++;
			}
			this.map = map.toArray(char[][]::new);
		}
	}

	public boolean doesLoop(Position obstruction) {
		guard.resetGuard();
		if (obstruction.equals(guard.getPosition())) {
			return false;
		}

		Map<Position, List<Direction>> pathing = new HashMap<>();

		Position currentPosition = guard.getPosition();

		while (isPositionInLocation(currentPosition)) {
			if (isLoop(currentPosition, guard.getFacing(), pathing)) {
				return true;
			}

			pathing.computeIfAbsent(currentPosition, (k) -> new ArrayList<>()).add(guard.getFacing());

			faceValidPosition(guard, obstruction);

			currentPosition = guard.move();
		}

		return false;

	}

	Set<Position> getDistinctPositions() {
		guard.resetGuard();
		Set<Position> positionsVisited = new HashSet<>();

		Position currentPosition = guard.getPosition();

		while (isPositionInLocation(currentPosition)) {
			positionsVisited.add(currentPosition);
			faceValidPosition(guard, null);
			currentPosition = guard.move();
		}

		return positionsVisited;
	}

	private boolean isLoop(Position position, Direction direction, Map<Position, List<Direction>> pathing) {
		if (!pathing.containsKey(position)) {
			return false;
		}

		return pathing.get(position)
				.contains(direction);
	}

	private void faceValidPosition(Guard guard, Position obstruction) {
		Position nextPosition = guard.getNextPosition();

		while (!isValidPosition(nextPosition) || (obstruction != null && obstruction.equals(nextPosition))) {
			guard.turn();
			nextPosition = guard.getNextPosition();
		}
	}

	private boolean isValidPosition(Position position) {
		if (!isPositionInLocation(position)) {
			return true;
		}
		return map[position.row()][position.column()] != OBSTACLE_SYMBOL;
	}

	private boolean isPositionInLocation(Position position) {
		return (position.row() >= 0 &&
				position.row() < map.length &&
				position.column() >= 0 &&
				position.column() < map[0].length);
	}
}

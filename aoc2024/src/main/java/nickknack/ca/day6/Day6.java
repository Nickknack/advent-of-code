package nickknack.ca.day6;

import java.util.Set;

public class Day6 {

	private static final String FILE_LOCATION = "actual/day6/input.txt";

	public static void main(String[] args) {
		RoomMap roomMap = new RoomMap(FILE_LOCATION);
		Set<Position> distinctPositions = roomMap.getDistinctPositions();

		System.out.printf("Part 1 Result: %s\n", distinctPositions.size());

		long loopTotal = distinctPositions.stream()
				.filter(roomMap::doesLoop)
				.count();

		System.out.printf("Part 2 Result: %s\n", loopTotal);
	}
}

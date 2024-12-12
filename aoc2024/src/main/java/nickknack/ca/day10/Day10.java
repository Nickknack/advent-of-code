package nickknack.ca.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day10 {
    private static final String TEST_INPUT = "test/day10/input.txt";
    private static final String INPUT = "actual/day10/input.txt";

    private static final boolean TEST = false;

    private static final int TRAILHEAD = 0;
    private static final int END = 9;

    public static void main(String[] args) {
        int[][] map = readInput(TEST ? TEST_INPUT : INPUT);
        part1(map);
        part2(map);
    }

    public static void part1(int[][] map) {
        System.out.println("Part 1 Start");

        long total = 0L;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == TRAILHEAD) {
                    Coordinate trailheadLocation = new Coordinate(i, j);
                    total += getTrailheadValue(trailheadLocation, map);
                }
            }
        }

        System.out.println("Part 1 result: " + total);
    }

    public static void part2(int[][] map) {
        System.out.println("Part 2 Start");

        long total = 0L;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == TRAILHEAD) {
                    Coordinate trailheadLocation = new Coordinate(i, j);
                    total += getTrailheadDistinctPaths(trailheadLocation, map);
                }
            }
        }

        System.out.println("Part 2 result: " + total);
    }

    private static long getTrailheadValue(Coordinate trailheadLocation, int[][] map) {
        long value = 0L;

        value += traversePaths(trailheadLocation, map)
                .stream().distinct().count();

        return value;
    }

    private static long getTrailheadDistinctPaths(Coordinate trailheadLocation, int[][] map) {
        long value = 0L;

        value += traversePaths(trailheadLocation, map)
                .size();


        return value;
    }

    private static List<Coordinate> traversePaths(Coordinate location, int[][] map) {
        if (location.x() < 0 || location.x() >= map[0].length || location.y() < 0 || location.y() >= map.length) {
            return new ArrayList<>();
        }

        if (map[location.x()][location.y()] == (END - 1)) {
            return getEndPathLocations(location, map);
        }

        List<Coordinate> endLocations = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate nextLocation = addDirection(location, direction);
            if (nextLocation.x() >= 0 && nextLocation.x() < map[0].length && nextLocation.y() >= 0 && nextLocation.y() < map.length && map[location.x()][location.y()] + 1 == map[nextLocation.x()][nextLocation.y()]) {
                endLocations.addAll(traversePaths(nextLocation, map));
            }
        }

        return endLocations;
    }

    private static Coordinate addDirection(Coordinate location, Direction direction) {
        return new Coordinate(location.x() + direction.getRowMover(), location.y() + direction.getColumnMover());
    }

    private static List<Coordinate> getEndPathLocations(Coordinate location, int[][] map) {
        List<Coordinate> endLocations = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate nextSpace = addDirection(location, direction);

            if (nextSpace.y() < map.length && nextSpace.y() >= 0 && nextSpace.x() < map[0].length && nextSpace.x() >= 0) {
                if (map[nextSpace.x()][nextSpace.y()] == END) {
                    endLocations.add(nextSpace);
                }
            }
        }

        return endLocations;
    }

    private static int[][] readInput(String fileLocation) {
        List<int[]> map = new ArrayList<>();
        try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
            while (scanner.hasNext()) {
                map.add(scanner.nextLine().chars().map(Character::getNumericValue).toArray());
            }
        }
        return map.toArray(int[][]::new);
    }
}

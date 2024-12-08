package nickknack.ca.day8;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 {
    private static final String TEST_INPUT = "test/day8/input.txt";
    private static final String INPUT = "actual/day8/input.txt";

    private static final boolean TEST = false;

    private static final char EMPTY_SPACE = '.';

    public static void main(String[] args) {
        char[][] map = readInput(TEST ? TEST_INPUT : INPUT);
        part1(map);
        part2(map);
    }

    private static void part1(char[][] map) {
        System.out.println("Part 1 start");

        Map<Character, List<Coordinate>> towerCoordinates = getCoordinatesOfTowers(map);

        Set<Coordinate> antinodeLocations = towerCoordinates.values().stream()
                .flatMap(coordinates -> getAntinodeLocations(coordinates, map.length - 1, map[0].length - 1, false).stream())
                .collect(Collectors.toSet());

        System.out.printf("Part 1 result: %s\n", antinodeLocations.size());
    }

    private static void part2(char[][] map) {
        System.out.println("Part 2 start");

        Map<Character, List<Coordinate>> towerCoordinates = getCoordinatesOfTowers(map);

        Set<Coordinate> antinodeLocations = towerCoordinates.values().stream()
                .flatMap(coordinates -> getAntinodeLocations(coordinates, map.length - 1, map[0].length - 1, true).stream())
                .collect(Collectors.toSet());

        System.out.printf("Part 2 result: %s\n", antinodeLocations.size());
    }


    private static Map<Character, List<Coordinate>> getCoordinatesOfTowers(char[][] map) {
        Map<Character, List<Coordinate>> towerLocations = new HashMap<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                char space = map[i][j];
                if (space != EMPTY_SPACE) {
                    towerLocations.computeIfAbsent(space, (k) -> new ArrayList<>()).add(new Coordinate(i, j));
                }
            }
        }

        return towerLocations;
    }

    private static Set<Coordinate> getAntinodeLocations(List<Coordinate> towerLocations, int maxRow, int maxColumn, boolean resonantHarmonics) {
        return towerLocations.stream()
                .flatMap(tower -> GetAntiNodesForLocation(tower, towerLocations, maxRow, maxColumn, resonantHarmonics).stream())
                .collect(Collectors.toSet());
    }

    private static Set<Coordinate> GetAntiNodesForLocation(Coordinate towerLocation, List<Coordinate> towerLocations, int maxRow, int maxColumn, boolean resonantHarmonics) {
        return towerLocations.stream()
                .flatMap(sisterLocation -> getValidAntinodes(towerLocation, sisterLocation, maxRow, maxColumn, resonantHarmonics).stream())
                .collect(Collectors.toSet());
    }

    private static Optional<Coordinate> getValidAntinode(Coordinate tower, Coordinate difference, int maxRow, int maxColumn) {
        int row = tower.row() + difference.row();
        int column = tower.column() + difference.column();

        if (row < 0 || row > maxRow || column < 0 || column > maxColumn) {
            return Optional.empty();
        }

        return Optional.of(new Coordinate(row, column));
    }

    private static Set<Coordinate> getValidAntinodes(Coordinate tower, Coordinate sisterTower, int maxRow, int maxColumn, boolean resonantHarmonics) {
        Coordinate antinodeDifference = getAntinodeDifference(tower, sisterTower);
        Optional<Coordinate> antinode = getValidAntinode(tower, antinodeDifference, maxRow, maxColumn);

        if (tower.equals(sisterTower)) {
            return new HashSet<>();
        }

        if (!resonantHarmonics) {
            return antinode.map(coordinate -> new HashSet<>(Set.of(coordinate))).orElseGet(HashSet::new);
        }

        Set<Coordinate> antinodes = new HashSet<>(Set.of(tower));

        while (antinode.isPresent()) {
            antinodes.add(antinode.get());
            antinode = getValidAntinode(antinode.get(), antinodeDifference, maxRow, maxColumn);

        }

        return antinodes;
    }

    private static Coordinate getAntinodeDifference(Coordinate tower, Coordinate sisterTower) {
        int antinodeRow = tower.row() - sisterTower.row();
        int antinodeColumn = tower.column() - sisterTower.column();

        return new Coordinate(antinodeRow, antinodeColumn);
    }

    private static char[][] readInput(String fileLocation) {
        List<char[]> map = new ArrayList<>();

        try (Scanner scanner = new Scanner(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)))) {
            while (scanner.hasNext()) {
                map.add(scanner.nextLine().toCharArray());
            }
        }

        return map.toArray(char[][]::new);
    }
}

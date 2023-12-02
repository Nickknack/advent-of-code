package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("input.txt"));
        final Map<String, Integer> MAX_VALUES = Map.of(
                "red", 12,
                "green", 13,
                "blue", 14
        );

        int total = 0;
        int power = 0;

        while (scanner.hasNext()) {
            String game = scanner.nextLine();
            String[] gameAndInput = game.split(":");

            power += getPowerForGame(gameAndInput[1]);
            if (isGameValid(gameAndInput[1], MAX_VALUES)) {
                total += Integer.valueOf(gameAndInput[0].split(" ")[1]);
            }
        }

        System.out.println(total);
        System.out.println(power);
    }

    private static boolean isGameValid(String game, Map<String, Integer> maxKinds) {
        String[] rounds = game.split(";");
        return Arrays.stream(rounds).allMatch(round -> isRoundValid(round, maxKinds));
    }

    private static boolean isRoundValid(String round, Map<String, Integer> maxKinds) {
        String[] pulls = round.split(",");

        for (String pull : pulls) {
            String[] countCategory = pull.stripLeading().split(" ");
            if (Integer.valueOf(countCategory[0]) > maxKinds.get(countCategory[1])) {
                return false;
            }
        }

        return true;
    }

    private static int getPowerForGame(String game) {
        Map<String, Integer> minimumNeededCounts = new HashMap<>();
        String[] rounds = game.split(";");
        Arrays.stream(rounds).forEach(round -> getValuesForRound(round).forEach((k, v) -> minimumNeededCounts.merge(k, v, Integer::max)));
        return minimumNeededCounts.values().stream().reduce(1, (val1, val2) -> val1 * val2);
    }

    private static Map<String, Integer> getValuesForRound(String round) {
        Map<String, Integer> counts = new HashMap<>();
        String[] pulls = round.split(",");

        for (String pull : pulls) {
            String[] countCategory = pull.stripLeading().split(" ");
            counts.put(countCategory[1], Integer.valueOf(countCategory[0]));
        }

        return counts;
    }
}
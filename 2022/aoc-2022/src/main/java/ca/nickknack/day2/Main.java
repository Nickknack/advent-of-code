package ca.nickknack.day2;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RoundReader roundReader = new RoundReader();

        List<Round> rounds = roundReader.readRounds("day2-input", false);
        System.out.println(String.format("Player score: %s", calculateTotalScore(rounds)));


        List<Round> roundsWithDesiredOutcome = roundReader.readRounds("day2-input", true);

        System.out.println(String.format("Player score with desired outcomes: %s", calculateTotalScore(roundsWithDesiredOutcome)));
    }

    private static int calculateTotalScore(List<Round> rounds) {
        return rounds.stream()
                .map(round -> round.getPlayerScore())
                .reduce(0, Integer::sum)
                .intValue();
    }
}
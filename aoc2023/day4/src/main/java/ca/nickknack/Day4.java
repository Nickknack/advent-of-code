package ca.nickknack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        int score = new BufferedReader(new InputStreamReader(Day4.class.getClassLoader().getResourceAsStream("input.txt")))
                .lines()
                .map(line -> lineToGame(line))
                .map(Card::getScore)
                .reduce(0, Integer::sum);

        System.out.println(score);
    }

    private static Card lineToGame(String line) {
        String[] cardNumberAndGame = line.replaceAll("Card\s*", "")
                .split(":");
        String[] splitLine = cardNumberAndGame[1]
                .split("\\|");

        return new Card(Integer.valueOf(cardNumberAndGame[0]), sectionToNumberList(splitLine[0]), sectionToNumberList(splitLine[1]));
    }

    private static List<Integer> sectionToNumberList(String section) {
        return Arrays.stream(section.stripLeading()
                .split("\\s+"))
                .map(Integer::valueOf)
                .toList();
    }


    //1: 1, 2: 2, 3: 4, 4: 8
    private record Card(int cardId, List<Integer> winningNumbers, List<Integer> picks) {

        public int getScore() {
            int winCount = winCount();
            if (winCount < 3) {
                return winCount;
            }

            int score = 2;
            for (int i = 3; i <= winCount; i++) {
                score *= 2;
            }

            return score;
        }
        private int winCount() {
            List<Integer> winningPicks = new ArrayList<>(picks);
            winningPicks.retainAll(winningNumbers);

            return winningPicks.size();
        }
    }
}
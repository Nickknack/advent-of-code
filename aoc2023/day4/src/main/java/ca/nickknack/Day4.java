package ca.nickknack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        Game game = new Game(new BufferedReader(new InputStreamReader(Day4.class.getClassLoader().getResourceAsStream("input.txt")))
                .lines().toList());

        System.out.println("part1: " + game.getPart1Score());
        System.out.println("part2: " + game.getPart2Score());
    }

    private static class Game {
        private List<Card> cards;

        Game(List<String> lines) {
            cards = lines.stream()
                    .map(this::lineToCard)
                    .toList();

            for (int i = 0; i < cards.size(); i++) {
                processScoreForCard(cards.get(i), cards.subList(i + 1, cards.size()));
            }
        }

        public int getPart1Score() {
            return cards.stream()
                    .map(Card::getScore)
                    .reduce(0, Integer::sum);
        }

        public int getPart2Score() {
            return cards.stream()
                    .map(Card::getCardCount)
                    .reduce(0, Integer::sum);
        }

        private void processScoreForCard(Card card, List<Card> nextCards) {
            for (int i = 0; i < card.getWinCount() && i < nextCards.size(); i++) {
                nextCards.get(i).increaseCardCount(card.cardCount);
            }
        }

        private Card lineToCard(String line) {
            String[] cardNumberAndGame = line.replaceAll("Card\s*", "")
                    .split(":");
            String[] splitLine = cardNumberAndGame[1]
                    .split("\\|");

            return new Card(Integer.valueOf(cardNumberAndGame[0]), sectionToNumberList(splitLine[0]), sectionToNumberList(splitLine[1]));
        }

        private List<Integer> sectionToNumberList(String section) {
            return Arrays.stream(section.stripLeading()
                            .split("\\s+"))
                    .map(Integer::valueOf)
                    .toList();
        }

    }

    private static class Card {
        private Integer cardId;
        private List<Integer> winningNumbers;
        private List<Integer> picks;
        private int cardCount;
        private int winCount;

        Card(Integer cardId, List<Integer> winningNumbers, List<Integer> picks) {
            this.cardId = cardId;
            this.winningNumbers = winningNumbers;
            this.picks = picks;
            this.winCount = winCount();
            cardCount = 1;
        }

        public int getCardId() {
            return cardId;
        }

        public int increaseCardCount(int increaseAmount) {
            return cardCount += increaseAmount;
        }

        public int getWinCount() {
            return this.winCount;
        }

        public int getCardCount() {
            return this.cardCount;
        }

        private int winCount() {
            List<Integer> winningPicks = new ArrayList<>(picks);
            winningPicks.retainAll(winningNumbers);

            return winningPicks.size();
        }

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
    }
}
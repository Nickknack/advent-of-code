package ca.nickknack.day2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Move {
    ROCK('X', 'A', 1),
    PAPER('Y', 'B', 2),
    SCISSORS('Z', 'C', 3);

    public final char playerCode;
    public final char opponentCode;
    public final int modifier;

    private static final Map<Move, Move> matchups = new HashMap<>();

    static {
        matchups.put(Move.ROCK, Move.SCISSORS);
        matchups.put(Move.SCISSORS, Move.PAPER);
        matchups.put(Move.PAPER, Move.ROCK);
    }

    private Move(char playerCode, char opponentCode, int modifier) {
        this.playerCode = playerCode;
        this.opponentCode = opponentCode;
        this.modifier = modifier;
    }

    public static Optional<Move> getByPlayerCode(char playerCode) {
        return Arrays.stream(Move.values())
                .filter(move -> move.playerCode == playerCode)
                .findFirst();
    }

    public static Optional<Move> getByOpponentCode(char opponentCode) {
        return Arrays.stream(Move.values())
                .filter(move -> move.opponentCode == opponentCode)
                .findFirst();
    }

    public boolean beats(Move move) {
        return matchups.get(this) == move;
    }

    public static Move getWinningMove(Move otherMove) {
        return matchups.entrySet().stream()
                .filter(entry -> entry.getValue() == otherMove)
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
    }

    public static Move getLosingMove(Move otherMove) {
        return matchups.get(otherMove);
    }
}

package ca.nickknack.day2;

import ca.nickknack.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoundReader {

    public List<Round> readRounds(String filename, boolean desiredOutcome) {
        List<Round> rounds = new ArrayList<>();
        Scanner scanner = ScannerUtil.newScanner(filename);

        while (scanner.hasNext()) {
            Move opponentMove = Move.getByOpponentCode(scanner.next().charAt(0)).orElseThrow(() -> new IllegalArgumentException("Invalid Player Move code"));
            if (desiredOutcome) {
                Score desiredScore = Score.getByCode(scanner.next().charAt(0)).orElseThrow(() -> new IllegalArgumentException("Invalid Score code"));

                rounds.add(new Round(opponentMove, desiredScore));
            } else {
                Move playerMove = Move.getByPlayerCode(scanner.next().charAt(0)).orElseThrow(() -> new IllegalArgumentException("Invalid Opponent Move code"));

                rounds.add(new Round(playerMove, opponentMove));
            }
        }

        return rounds;
    }
}

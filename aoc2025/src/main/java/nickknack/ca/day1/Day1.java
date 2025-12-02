package nickknack.ca.day1;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day1 {
    //6932
    private static final int INITIAL_POSITION = 50;
    private static final int OUT_OF_BOUNDS = 100;

    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';

    static void main() {
        CompletableFuture<Void> test = CompletableFuture.supplyAsync(() -> day1(true))
                .thenAccept(result -> System.out.printf("Part 1 Test: %s\nPart 2 Test: %s\n\n", result.timesLandedOnZero(), result.totalZeros()));

        CompletableFuture<Void> actual = CompletableFuture.supplyAsync(() -> day1(false))
                .thenAccept(result -> System.out.printf("Part 1: %s\nPart 2: %s\n\n", result.timesLandedOnZero(), result.totalZeros()));

        CompletableFuture.allOf(test, actual).join();
    }

    private static DayResult day1(boolean test) {
        List<String> lines = FileReader.readFile(1, test ? DayPart.TEST : DayPart.PART_1);

        int currentPosition = INITIAL_POSITION;
        int timesLandedOnZero = 0;
        int totalTimesPassedZero = 0;

        for (int i = 0; i < lines.size(); i++) {
            TurnResult result = turnLock(currentPosition, lines.get(i));
            currentPosition = result.position();

            if (result.position() == 0) {
                timesLandedOnZero++;
            }
            totalTimesPassedZero += result.cycles();
        }

        return new DayResult(timesLandedOnZero, totalTimesPassedZero);
    }

    private static TurnResult turnLock(int currentPosition, String move) {
        char direction = move.charAt(0);
        int amount = Integer.valueOf(move.substring(1, move.length()));

        int fullRotations = amount / OUT_OF_BOUNDS;
        int remainder = amount % OUT_OF_BOUNDS;

        if (direction == RIGHT && currentPosition + remainder >= OUT_OF_BOUNDS) {
            fullRotations++;
        } else if (direction == LEFT && currentPosition != 0 && currentPosition - remainder <= 0) {
            fullRotations++;
        }


        int newPosition = direction == RIGHT ? currentPosition + amount : currentPosition - amount;

        int finalPosition = BigInteger.valueOf(newPosition).mod(BigInteger.valueOf(OUT_OF_BOUNDS)).intValue();

        return new TurnResult(finalPosition, fullRotations);
    }
}
package nickknack.ca.day3;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day3 {
	static void main() {
		List<String> testData = FileReader.readFile(3, DayPart.TEST);
        List<String> actualData = FileReader.readFile(3, DayPart.PART_1);

//		CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> part1(testData))
//				.thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));
//
//        CompletableFuture<Void> part1 = CompletableFuture.supplyAsync(() -> part1(actualData))
//                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture<Void> part2Test = CompletableFuture.supplyAsync(() -> part2(testData))
                .thenAccept(result -> System.out.printf("Part 2 Test: %s\n\n", result));

        CompletableFuture<Void> part2 = CompletableFuture.supplyAsync(() -> part2(actualData))
                .thenAccept(result -> System.out.printf("Part 2: %s\n\n", result));

        CompletableFuture.allOf(part2Test, part2).join();
	}

	private static long part1(List<String> data) {
        return data.stream()
                .map(Day3::part1)
                .reduce(0L, Long::sum);
	}

    private static long part2(List<String> data) {
        return data.stream()
                .map(line -> part2(line, 12))
                .peek(jolt -> System.out.println(jolt))
                .reduce(0L, Long::sum);
    }

	private static long part1(String bank) {
		int currentLeft = 0;
        int currentRight = 0;
		for (int i = 0; i < bank.length() - 1; i++) {
            int leftVal = Character.getNumericValue(bank.charAt(i));
			if (leftVal > currentLeft) {
				currentLeft = leftVal;
                currentRight = 0;
			}

			for (int j = i + 1; j < bank.length(); j++) {
                int rightVal = Character.getNumericValue(bank.charAt(j));

				if (j < bank.length() - 1 && rightVal > currentLeft) {
					currentLeft = rightVal;
                    currentRight = 0;
                    i = j;
				} else if (rightVal > currentRight) {
					currentRight = rightVal;
				}
			}
		}
        return currentLeft * 10L + currentRight;
	}

    private static long part2(String bank, int size) {
        StringBuilder batteryBuilder = new StringBuilder();
        int lastIndex = -1;
        for (int i = 0; i < size; i++) {
            int index = getLargestBatteryIndex(bank.substring(lastIndex + 1, bank.length() + 1 - size + i));
            lastIndex += index + 1;
            batteryBuilder.append(bank.charAt(lastIndex));
        }

        return Long.valueOf(batteryBuilder.toString());
    }

    private static int getLargestBatteryIndex(String bank) {
        int indexOfLargest = 0;
        int currentLargest = 0;
        for (int i = 0; i < bank.length(); i++) {
            int val = Character.getNumericValue(bank.charAt(i));

            if (val > currentLargest) {
                currentLargest = val;
                indexOfLargest = i;

                if (val == 9) {
                    break;
                }
            }
        }

        return indexOfLargest;
    }
}
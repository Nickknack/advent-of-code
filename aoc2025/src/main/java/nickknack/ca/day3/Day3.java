package nickknack.ca.day3;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day3 {
	static void main() {
		List<String> testData = FileReader.readFile(3, DayPart.TEST);
        List<String> actualData = FileReader.readFile(3, DayPart.PART_1);

		CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> day3(testData))
				.thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> part1 = CompletableFuture.supplyAsync(() -> day3(actualData))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture.allOf(testPart1, part1).join();
	}

	private static long day3(List<String> data) {
        return data.stream()
                .map(Day3::getJoltageForBank)
                .peek(jolt -> System.out.println(jolt))
                .reduce(0L, Long::sum);
	}

	private static long getJoltageForBank(String bank) {
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
}
//17638
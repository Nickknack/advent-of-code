package nickknack.ca.day3;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day3 {
	static void main() {
		List<String> testData = FileReader.readFile(3, DayPart.TEST);

		CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> day3(testData))
				.thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));
	}

	private static long day3(List<String> data) {

	}

	private static long getJoltageForBank(String bank) {
		long currentLeft = 0;
		long currentRight = 0;
		for (int i = 0; i < bank.length() - 1; i++) {
			long leftVal = Long.valueOf(bank.charAt(i));
			if (leftVal > currentLeft) {
				currentLeft = leftVal;
				currentRight = Long.valueOf(bank.charAt(i + 1));
			}

			for (int j = i + 2; j < bank.length(); j++) {
				long rightVal = Long.valueOf(bank.charAt(j));

				if (j < bank.length() - 2 && rightVal > currentLeft) {
					currentLeft = rightVal;
					currentRight = Long.valueOf(bank.charAt(j + 1));
					i = j + 1;
					break;
				}

				if (rightVal > currentRight) {
					currentRight = rightVal;
				}
			}
		}
	}
}

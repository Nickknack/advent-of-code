package nickknack.ca.day3;

import nickknack.ca.common.DayPart;
import nickknack.ca.common.FileReader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Day3 {
    private static final int BATTERY_AMOUNT_PART_1 = 2;
    private static final int BATTERY_AMOUNT_PART_2 = 12;


    static void main() {
		List<String> testData = FileReader.readFile(3, DayPart.TEST);
        List<String> actualData = FileReader.readFile(3, DayPart.PART_1);

		CompletableFuture<Void> testPart1 = CompletableFuture.supplyAsync(() -> calculateTotalVoltage(testData, BATTERY_AMOUNT_PART_1))
				.thenAccept(result -> System.out.printf("Part 1 Test: %s\n\n", result));

        CompletableFuture<Void> part1 = CompletableFuture.supplyAsync(() -> calculateTotalVoltage(actualData, BATTERY_AMOUNT_PART_1))
                .thenAccept(result -> System.out.printf("Part 1: %s\n\n", result));

        CompletableFuture<Void> part2Test = CompletableFuture.supplyAsync(() -> calculateTotalVoltage(testData, BATTERY_AMOUNT_PART_2))
                .thenAccept(result -> System.out.printf("Part 2 Test: %s\n\n", result));

        CompletableFuture<Void> part2 = CompletableFuture.supplyAsync(() -> calculateTotalVoltage(actualData, BATTERY_AMOUNT_PART_2))
                .thenAccept(result -> System.out.printf("Part 2: %s\n\n", result));

        CompletableFuture.allOf(testPart1, part1, part2Test, part2).join();
	}

    private static long calculateTotalVoltage(List<String> data, int batteryAmount) {
        return data.stream()
                .map(line -> getVoltageForBank(line, batteryAmount))
                .reduce(0L, Long::sum);
    }

    private static long getVoltageForBank(String bank, int batteryAmount) {
        StringBuilder batteryBuilder = new StringBuilder();
        int start = 0;
        int end = bank.length() + 1 - batteryAmount;
        for (int i = 0; i < batteryAmount; i++) {
            int index = getLargestBatteryIndex(bank.substring(start, end + i));
            batteryBuilder.append(bank.charAt(start + index));
            start += index + 1;
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
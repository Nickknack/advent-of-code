package nickknack.ca.day2;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

class Part2 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		int validReports = 0;
		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("actual/day2/input.txt"))) {
			while (scanner.hasNext()) {
				String[] lineNumbers = scanner.nextLine().split(" ");

				if (isReportValid(lineNumbers)) {
					validReports++;
				} else {
					for (int i = 0; i < lineNumbers.length; i++) {
						if (isReportValid(removeItemAtIndex(lineNumbers, i))) {
							validReports++;
							break;
						}
					}
				}
			}
		}

		System.out.println(validReports);
	}

	private static String[] removeItemAtIndex(String[] array, int index) {
		String[] newArray = new String[array.length - 1];

		int counter = 0;
		for (int i = 0; i < array.length; i++) {
			if (i != index) {
				newArray[counter] = array[i];
				counter ++;
			}
		}

		return newArray;
	}

	private static boolean isReportValid(String[] line) {
		boolean isIncrement = Integer.parseInt(line[0]) < Integer.parseInt(line[1]);

		for (int i = 0; i < line.length - 1; i++) {
			int num1 = Integer.parseInt(line[i]);
			int num2 = Integer.parseInt(line[i + 1]);

			if (!isValidRange(Math.abs(num1 - num2)) || !isValidDirection(isIncrement, num1, num2)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isValidDirection(boolean isIncrement, int num1, int num2) {
		return (isIncrement && num1 < num2) || (!isIncrement && num1 > num2);
	}

	private static boolean isValidRange(int difference) {
		return difference > 0 && difference < 4;
	}
}

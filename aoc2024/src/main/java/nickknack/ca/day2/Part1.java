package nickknack.ca.day2;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

class Part1 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		int validReports = 0;
		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("actual/day2/input.txt"))) {
			while (scanner.hasNext()) {
				String[] lineNumbers = scanner.nextLine().split(" ");
				boolean isValid = true;
				boolean isIncrement = Integer.parseInt(lineNumbers[0]) < Integer.parseInt(lineNumbers[1]);

				for (int i = 0; i < lineNumbers.length - 1; i++) {
					int num1 = Integer.parseInt(lineNumbers[i]);
					int num2 = Integer.parseInt(lineNumbers[i + 1]);

					if (!isValidRange(Math.abs(num1 - num2)) || !isValidDirection(isIncrement, num1, num2)) {
						isValid = false;
						break;
					}
				}

				if (isValid) {
					validReports++;
				}
			}
		}

		System.out.println(validReports);
	}

	private static boolean isValidDirection(boolean isIncrement, int num1, int num2) {
		return (isIncrement && num1 < num2) || (!isIncrement && num1 > num2);
	}

	private static boolean isValidRange(int difference) {
		return difference > 0 && difference < 4;
	}
}

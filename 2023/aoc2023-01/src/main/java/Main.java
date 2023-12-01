import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import static java.util.Map.Entry;

public class Main {	public static void main(String[] args) throws FileNotFoundException {
	Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("test2.txt"));
	List<Integer> lineValues = new ArrayList<>();

	while (scanner.hasNext()) {
		lineValues.add(getDigitsForLine(lineDigitMapper(scanner.next())));
	}

	System.out.println(lineValues.stream().reduce(0, Integer::sum));
}

	private static Integer getDigitsForLine(String line) {
		Optional<String> firstInt = Optional.empty();
		Optional<String> lastInt = Optional.empty();

		for (int i = 0; firstInt.isEmpty() || lastInt.isEmpty(); i++) {
			if (firstInt.isEmpty()) {
				firstInt = getCharAt(i, line);
			}

			if (lastInt.isEmpty()) {
				lastInt = getCharAt(line.length() - 1 - i, line);
			}
		}

		return Integer.valueOf(firstInt.get() + lastInt.get());
	}

	private static Optional<String> getCharAt(int index, String line) {
		char maybeNumber = line.charAt(index);
		if (Character.isDigit(maybeNumber)) {
			return Optional.of(String.valueOf(maybeNumber));
		}
		return Optional.empty();
	}

	private static String lineDigitMapper(String line) {
	String newString = line;

		Map<String, String> numberMappings = Map.of(
				"one", "o1e",
				"two", "t2o",
				"three", "t3e",
				"four", "f4r",
				"five", "f5e",
				"six", "s6x",
				"seven", "s7n",
				"eight", "e8t",
				"nine", "n9e",
				"zero", "z0o"
		);

		for (Entry<String, String> entry: numberMappings.entrySet()) {
			newString  = newString.replaceAll(entry.getKey(), entry.getValue());
		}

		return newString;
	}
}

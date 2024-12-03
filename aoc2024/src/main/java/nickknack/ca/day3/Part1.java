package nickknack.ca.day3;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
	private static final Pattern MULT_REGEX_PATTERN = Pattern.compile("mul[(]([0-9]{1,3}),([0-9]{1,3})[)]", Pattern.MULTILINE);

	public static void main(String[] args) {
		int total = 0;
		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("test/day3/input.txt"))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				Matcher matcher = MULT_REGEX_PATTERN.matcher(line);
				while (matcher.find()) {
					total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
				}
			}
		}

		System.out.println(total);
	}
}

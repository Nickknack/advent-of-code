package nickknack.ca.day5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Part1 {
	public static void main(String[] args) {
		Map<Integer, List<Integer>> rules = new HashMap<>();

		try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("actual/day5/input.txt"))) {
			boolean isRules = true;
			int total = 0;
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					isRules = false;
					continue;
				}

				if (isRules) {
					String[] values = line.split("\\|");
					rules.computeIfAbsent(Integer.parseInt(values[0]), k -> new ArrayList<>()).add(Integer.parseInt(values[1]));
				} else {
					String[] updatePages = line.split(",");
					Optional<Integer> middlePage = getMiddlePage(updatePages, rules);

					if (middlePage.isPresent()) {
						total += middlePage.get();
					}
				}
			}
			System.out.println(total);
		}
	}

	private static Optional<Integer> getMiddlePage(String[] updatePages, Map < Integer, List < Integer >> rules){
		Map<Integer, Integer> pageHits = new HashMap<>();
		for (String updatePage : updatePages) {
			int page = Integer.parseInt(updatePage);

			Optional<List<Integer>> pagesComeAfter = Optional.ofNullable(rules.get(page));

			if (pagesComeAfter.isPresent() && pagesComeAfter.get().stream()
					.anyMatch(p -> pageHits.containsKey(p))) {
				return Optional.empty();
			}

			pageHits.put(page, pageHits.getOrDefault(page, 0) + 1);
		}

		return Optional.of(Integer.parseInt(updatePages[updatePages.length / 2]));
	}
}

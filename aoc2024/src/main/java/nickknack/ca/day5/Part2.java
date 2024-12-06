package nickknack.ca.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class Part2 {
	public static void main(String[] args) {
			Map<String, List<String>> mustBeAfter = new HashMap<>();

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
					mustBeAfter.computeIfAbsent(values[1], k -> new ArrayList<>()).add(values[0]);
				} else {
					String[] updatePages = line.split(",");
					String middlePage = getMiddlePage(updatePages, mustBeAfter);
					total += Integer.parseInt(middlePage);
				}
			}
			System.out.println(total);
		}
	}

	private static String getMiddlePage(String[] updatePages, Map<String, List<String>> mustBeAfter){
		Map<String, Integer> pageHits = new HashMap<>();
		String[] validUpdate = Arrays.copyOf(updatePages, updatePages.length);

		for (String updatePage : updatePages) {
			Optional<List<String>> pagesMustComeAfter = Optional.ofNullable(mustBeAfter.get(updatePage));

			if (pagesMustComeAfter.isEmpty() || pagesMustComeAfter.get().stream()
					.filter(p -> Arrays.asList(updatePages).contains(p))
					.allMatch(pageHits::containsKey)) {
				pageHits.put(updatePage, pageHits.getOrDefault(updatePage, 0) + 1);
			} else {
				return getValidUpdate(updatePages, mustBeAfter)[updatePages.length / 2];
			}
		}

		return "0";
	}

	private static String[] getValidUpdate(String[] updatePages, Map<String, List<String>> mustBeAfter) {
		List<String> validUpdate = new ArrayList<>();
		Map<String, Boolean> hasBeenAdded = Arrays.stream(updatePages).collect(Collectors.toMap(v -> v, v -> false));

		for (String page: updatePages) {
			validUpdate.addAll(getPagesToAdd(page, hasBeenAdded, mustBeAfter));
		}

		return validUpdate.toArray(String[]::new);
	}

	private static List<String> getPagesToAdd(String page, Map<String, Boolean> hasBeenAdded, Map<String, List<String>> mustBeAfter) {
		List<String> valuesToAdd = new ArrayList<>();

		List<String> mustBeAfterInUpdate = mustBeAfter.getOrDefault(page, new ArrayList<>()).stream()
				.filter(hasBeenAdded::containsKey)
				.filter(not(hasBeenAdded::get))
				.toList();
		if (!mustBeAfterInUpdate.isEmpty()) {
			valuesToAdd.addAll(mustBeAfterInUpdate.stream()
					.flatMap(p -> getPagesToAdd(p, hasBeenAdded, mustBeAfter).stream())
					.toList());
		}

		if (!hasBeenAdded.get(page)) {
			valuesToAdd.add(page);
			hasBeenAdded.put(page, true);
		}

		return valuesToAdd;
	}
}

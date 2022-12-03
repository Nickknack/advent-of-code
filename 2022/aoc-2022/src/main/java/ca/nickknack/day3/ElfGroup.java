package ca.nickknack.day3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ElfGroup {
    List<Rucksack> rucksacks;

    public ElfGroup(List<Rucksack> rucksacks) {
        this.rucksacks = rucksacks;
    }

    public int getBadgePriority() {
        return getCommonItems().stream()
                .map(item -> PriorityUtil.getPriority(item))
                .reduce(0, Integer::sum)
                .intValue();
    }

    private Set<Character> getCommonItems() {
        Set<Character> sharedItems = new HashSet<>();

        sharedItems.addAll(rucksacks.get(0).getItems());

        for (int i = 1; i < rucksacks.size(); i++) {
            if (sharedItems.isEmpty()) {
                return sharedItems;
            }

            sharedItems = getMatchingItems(rucksacks.get(i), sharedItems);
        }

        return sharedItems;
    }


    private Set<Character> getMatchingItems(Rucksack rucksack, Set<Character> items) {
        return CharacterUtil.getMatchingItemSet(new HashSet<>(rucksack.getItems()), items);
    }
}

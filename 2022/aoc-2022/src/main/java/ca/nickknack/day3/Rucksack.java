package ca.nickknack.day3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Rucksack {
    private List<Compartment> compartments = new ArrayList<>();

    public void addCompartments(List<Compartment> compartments) {
        this.compartments.addAll(compartments);
    }

    public int getTotalPriorityOfCommonItems() {
        return getCommonItems().stream()
                .map(character -> PriorityUtil.getPriority(character))
                .reduce(0, Integer::sum)
                .intValue();
    }

    public List<Character> getItems() {
        return compartments.stream()
                .flatMap(compartment -> compartment.getItems().stream())
                .collect(Collectors.toList());
    }

    private Set<Character> getCommonItems() {
        Set<Character> sharedItems = new HashSet<>();
        sharedItems.addAll(compartments.get(0).getItems());

        for (int i = 1; i < compartments.size(); i++) {
            if (sharedItems.isEmpty()) {
                return sharedItems;
            }

            sharedItems = getMatchingItems(compartments.get(i), sharedItems);
        }

        return sharedItems;
    }

    private Set<Character> getMatchingItems(Compartment compartment, Set<Character> items) {
        return CharacterUtil.getMatchingItemSet(new HashSet<>(compartment.getItems()), items);
    }
}

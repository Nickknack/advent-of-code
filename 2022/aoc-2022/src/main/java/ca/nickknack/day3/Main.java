package ca.nickknack.day3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RucksackReader reader = new RucksackReader();
        List<Rucksack> rucksacks = reader.readRucksacks("day3-input", 2);
        int totalPriority = rucksacks.stream()
                .map(rucksack -> rucksack.getTotalPriorityOfCommonItems())
                .reduce(0, Integer::sum)
                .intValue();

        System.out.println(String.format("Total priorty: %s", totalPriority));

        List<ElfGroup> groups = groupRucksacks(rucksacks, 3);

        int groupBadgePriority = groups.stream()
                .map(group -> group.getBadgePriority())
                .reduce(0, Integer::sum)
                .intValue();

        System.out.println(String.format("Total badge priorty: %s", groupBadgePriority));
    }

    private static List<ElfGroup> groupRucksacks(List<Rucksack> rucksacks, int groupSize) {
        if (rucksacks.size() % groupSize != 0) {
            throw new IllegalArgumentException(String.format("rucksacks can't be divided into groups of %s", groupSize));
        }
        List<ElfGroup> groups = new ArrayList<>();

        for (int i = 0; i < rucksacks.size(); i += groupSize) {
            groups.add(new ElfGroup(rucksacks.subList(i, i + groupSize)));
        }

        return groups;
    }
}

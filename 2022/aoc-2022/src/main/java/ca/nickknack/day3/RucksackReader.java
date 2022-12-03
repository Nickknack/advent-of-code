package ca.nickknack.day3;

import ca.nickknack.day2.Move;
import ca.nickknack.day2.Round;
import ca.nickknack.day2.Score;
import ca.nickknack.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RucksackReader {

    public List<Rucksack> readRucksacks(String filename, int compartments) {
        List<Rucksack> rucksacks = new ArrayList<>();
        Scanner scanner = ScannerUtil.newScanner(filename);

        while (scanner.hasNext()) {
            Rucksack rucksack = new Rucksack();
            String line = scanner.nextLine();
            rucksack.addCompartments(lineToCompartments(compartments, line));
            rucksacks.add(rucksack);
        }

        return rucksacks;
    }

    private List<Compartment> lineToCompartments(int compartmentAmount, String line) {
        if (!doItemsDivideEvenly(compartmentAmount, line)) {
            throw new IllegalArgumentException(String.format("line %s can't be divided evenly between %s compartments", line, compartmentAmount));
        }

        List<Compartment> compartments = new ArrayList<>();
        int itemsPerCompartment = line.length() / compartmentAmount;

        for (int i = 0; i < compartmentAmount; i++) {
            int start = itemsPerCompartment * i;
            String compartmentItems = line.substring(start, start + itemsPerCompartment);

            compartments.add(new Compartment(CharacterUtil.stringToCharacters(compartmentItems)));
        }

        return compartments;
    }

    private boolean doItemsDivideEvenly(int compartmentAmount, String line) {
        return line.length() % compartmentAmount == 0;
    }
}

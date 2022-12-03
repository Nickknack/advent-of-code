package ca.nickknack.day1;

import ca.nickknack.util.ScannerUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalorieReader {
    public List<Elf> readElfCaloriesFromFile(String filename) {
        List<Elf> elves = new ArrayList<>();

        Scanner scanner = ScannerUtil.newScanner(filename);

        while (scanner.hasNext()) {
            Elf elf = new Elf();
            readCaloriesForElf(scanner, elf);
            elves.add(elf);
        }

        return elves;
    }

    private void readCaloriesForElf(Scanner scanner, Elf elf) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                return;
            }
            elf.addCalorieEntry(Integer.valueOf(line));
        }
    }
}

package ca.nickknack;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalorieReader {
    public List<Elf> readElfCaloriesFromFile(String filename) {
        List<Elf> elves = new ArrayList<>();

        Scanner scanner = getScanner(filename);

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

    private Scanner getScanner(String filename) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        return new Scanner(inputStream);
    }
}

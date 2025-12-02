package nickknack.ca.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static List<String> readFile(int day, DayPart part) {
        List<String> lines = new ArrayList<>();
        String filepathBase = DayPart.TEST == part ? "test" : "actual";
        String filepath = "%s/day%s/%s.txt".formatted(filepathBase, day, part.getValue());
        try (
                Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath))) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }

        return lines;
    }
}
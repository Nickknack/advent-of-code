package nickknack.ca.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static List<String> readFile(int day, DayPart part) {
        return read(day, part, null);
    }

    public static List<String> readFileByDelimeter(int day, DayPart part, String delimiter) {
        return read(day, part, delimiter);
    }

    private static List<String> read(int day, DayPart part, String delimiter) {
        List<String> lines = new ArrayList<>();
        String filepathBase = DayPart.TEST == part ? "test" : "actual";
        String filepath = "%s/day%s/%s.txt".formatted(filepathBase, day, part.getValue());
        try (
                Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath))) {

            if (delimiter != null) {
                scanner.useDelimiter(delimiter);
            }

            while (scanner.hasNext()) {
                if (delimiter != null) {
                    lines.add(scanner.next());
                } else {
                    lines.add(scanner.nextLine());
                }
            }
        }

        return lines;
    }
}

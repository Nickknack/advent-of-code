package ca.nickknack.day8;

import ca.nickknack.util.ScannerUtil;

import java.util.Scanner;

public class ForestReader {

    public Forest generateForestFromInput(String filename) {
        int rowCount = getRowSize(filename);
        int columnCount = getColumnSize(filename);

        Forest forest = new Forest(rowCount, columnCount);

        Scanner scanner = ScannerUtil.newScanner(filename);

        for (int i = 0; i < rowCount; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < columnCount; j++) {
                forest.addTree(i, j, Character.getNumericValue(line.charAt(j)));
            }
        }

        return forest;
    }

    private int getRowSize(String filename) {
        Scanner scanner = ScannerUtil.newScanner(filename);
        int rowCount = 0;

        while (scanner.hasNextLine()) {
            scanner.nextLine();
            rowCount++;
        }

        scanner.close();

        return rowCount;
    }

    private int getColumnSize(String filename) {
        Scanner scanner = ScannerUtil.newScanner(filename);
        if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException(String.format("%s does not have a valid forest grid", filename));
        }

        String line = scanner.nextLine();

        scanner.close();

        return line.length();
    }
}

package ca.nickknack.day7;

import ca.nickknack.util.ScannerUtil;

import java.util.Scanner;

public class FilesystemReader {
    public Directory createFilesystem(String filename) {
        Scanner scanner = ScannerUtil.newScanner(filename);
        Directory rootDirectory = new Directory("/", null);
        Directory currentDirectory = rootDirectory;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            currentDirectory = processLine(line, currentDirectory);
        }

        scanner.close();

        return rootDirectory;
    }

    private Directory processLine(String line, Directory directory) {
        String[] parts = line.split(" ");

        if (parts[0].equals("$")) {
            if (parts[1].equals("cd")) {
                return directory.cd(parts[2]);
            }
        } else {
            if (parts[0].equals("dir")) {
                directory.addDirectory(parts[1]);
            } else {
                directory.addFile(new File(parts[1], Integer.valueOf(parts[0])));
            }
        }

        return directory;
    }
}
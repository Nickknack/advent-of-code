package ca.nickknack.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int maxDirectorySize = 100000;
        int totalSpace = 70000000;
        int requiredUnusedSpace = 30000000;

        FilesystemReader filesystemReader = new FilesystemReader();

        Directory rootDirectory = filesystemReader.createFilesystem("day7-input");

        int result1 = calculateSumOfAllDirectoriesWithMaximumSize(maxDirectorySize, rootDirectory);

        System.out.println(String.format("The sum of all directories with a maximum size of %s is %s", maxDirectorySize, result1));

        System.out.println(String.format("The size of the directory you must delete to have the required space of %s is %s", requiredUnusedSpace, calculateSizeOfSmallestDirectoryRequiredForSpaceNeeded(totalSpace, requiredUnusedSpace, rootDirectory)));
    }

    private static int calculateSumOfAllDirectoriesWithMaximumSize(int maximumSize, Directory rootDirectory) {
        List<Directory> allDirectories = new ArrayList<>(Arrays.asList(rootDirectory));
        allDirectories.addAll(rootDirectory.getAllChildren());

        return allDirectories.stream()
                .map(Directory::getSize)
                .filter(size -> size <= maximumSize)
                .reduce(0, Integer::sum)
                .intValue();
    }

    private static int calculateSizeOfSmallestDirectoryRequiredForSpaceNeeded(int totalSpace, int sizeNeeded, Directory rootDirectory) {
        int rootSize = rootDirectory.getSize();
        int sizeNeededToDelete = sizeNeeded - (totalSpace - rootSize);

        return rootDirectory.getAllChildren().stream()
                .map(Directory::getSize)
                .filter(size -> size >= sizeNeededToDelete)
                .mapToInt(value -> value)
                .min()
                .getAsInt();


    }
}

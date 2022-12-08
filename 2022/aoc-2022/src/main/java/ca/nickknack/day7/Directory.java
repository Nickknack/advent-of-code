package ca.nickknack.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Directory {

    private String name;
    private List<File> files = new ArrayList<>();
    private Directory parent;
    private List<Directory> directories = new ArrayList<>();

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public Directory addDirectory(String directoryName) {
        if (!isDirectoryNameUnique(directoryName)) {
            throw new IllegalArgumentException(String.format("directory with name %s already exists at this level", directoryName));
        }
        Directory directory = new Directory(directoryName, this);
        directories.add(directory);

        return directory;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public int getSize() {
         return getDirectFileSizes() + getChildDirectoriesSizes();
    }

    public List<Directory> getAllChildren() {
        List<Directory> allChildren = new ArrayList<>(directories);
        allChildren.addAll(directories.stream()
                .flatMap(directory -> directory.getAllChildren().stream())
                .collect(Collectors.toList()));

        return allChildren;
    }

    public Directory cd(String directoryName) {
        if (directoryName.equals("/")) {
            return cdRoot();
        }

        if (directoryName.equals("..")) {
            return parent;
        }

        return directories.stream()
                .filter(directory -> directory.name.equals(directoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("a directory named %s already exists in this directory", directoryName)));
    }

    private boolean isDirectoryNameUnique(String name) {
        return directories.stream().noneMatch(directory -> directory.name.equals(name));
    }

    private Directory cdRoot() {
        if (parent == null) {
            return this;
        }

        return parent.cdRoot();
    }

    private int getDirectFileSizes() {
        return files.stream()
                .map(File::getSize)
                .reduce(0, Integer::sum)
                .intValue();
    }

    private int getChildDirectoriesSizes() {
        return directories.stream()
                .map(Directory::getSize)
                .reduce(0, Integer::sum)
                .intValue();
    }
}

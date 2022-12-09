package ca.nickknack.day8;

public class Main {
    public static void main(String[] args) {
        ForestReader forestReader = new ForestReader();

        Forest forest = forestReader.generateForestFromInput("day8-input");

        System.out.println(String.format("There are %s visible trees", forest.visibleTreesCount()));

        System.out.println(String.format("The best tree scenic score is %s", forest.calculateGreatestScenicScore()));
    }
}

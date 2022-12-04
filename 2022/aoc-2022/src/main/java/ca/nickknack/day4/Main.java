package ca.nickknack.day4;

import ca.nickknack.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<ZoneGroup> zoneGroups = getZoneGroups("day4-input");

        int fullyOverlapCount = zoneGroups.stream()
                .filter(zoneGroup -> zoneGroup.doZonesFullyOverlap())
                .collect(Collectors.toList()).size();

        System.out.println(String.format("there are %s fully overlapping zones", fullyOverlapCount));


        List<ZoneGroup> overlapCount = zoneGroups.stream()
                .filter(zoneGroup -> zoneGroup.doZonesOverlap())
                .collect(Collectors.toList());

        System.out.println(String.format("there are %s overlapping zones", overlapCount.size()));
    }

    private static List<ZoneGroup> getZoneGroups(String filename) {
        List<ZoneGroup> zoneGroups = new ArrayList<>();
        Scanner scanner = ScannerUtil.newScanner(filename);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            zoneGroups.add(new ZoneGroup(line));
        }

        return zoneGroups;
    }
}

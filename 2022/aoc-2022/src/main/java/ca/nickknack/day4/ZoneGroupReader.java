package ca.nickknack.day4;

import ca.nickknack.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZoneGroupReader {
    public List<ZoneGroup> getZoneGroups(String filename) {
        List<ZoneGroup> zoneGroups = new ArrayList<>();
        Scanner scanner = ScannerUtil.newScanner(filename);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            zoneGroups.add(new ZoneGroup(line));
        }

        return zoneGroups;
    }
}

package ca.nickknack.day4;

public class ZoneGroup {
    private Zone zone1;
    private Zone zone2;

    public ZoneGroup(String zoneGroupString) {
        String[] zones = zoneGroupString.split(",");

        this.zone1 = new Zone(zones[0]);
        this.zone2 = new Zone(zones[1]);
    }

    public boolean doZonesFullyOverlap() {
        return doesZoneFullyOverlap(zone1, zone2) || doesZoneFullyOverlap(zone2, zone1);
    }

    public boolean doZonesOverlap() {
        return doesZoneOverlap(zone1, zone2) || doesZoneOverlap(zone2, zone1);
    }

    private boolean doesZoneOverlap(Zone zone, Zone otherZone) {
        return isPointWithinRange(zone.getStart(), otherZone.getStart(), otherZone.getEnd()) || isPointWithinRange(zone.getEnd(), otherZone.getStart(), otherZone.getEnd());
    }
    private boolean doesZoneFullyOverlap(Zone zone, Zone otherZone) {
        return zone.getStart() >= otherZone.getStart() && zone.getEnd() <= otherZone.getEnd();
    }

    private boolean isPointWithinRange(int point, int start, int end) {
        return point >= start && point <= end;
    }
}

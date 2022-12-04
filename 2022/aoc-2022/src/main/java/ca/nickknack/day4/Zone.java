package ca.nickknack.day4;

public class Zone {
    private int start;
    private int end;

    public Zone(String zoneString) {
        String[] zoneRange = zoneString.split("-");
        this.start = Integer.valueOf(zoneRange[0]);
        this.end = Integer.valueOf(zoneRange[1]);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

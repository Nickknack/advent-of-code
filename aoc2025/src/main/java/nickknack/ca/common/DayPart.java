package nickknack.ca.common;

public enum DayPart {
    PART_1("part1"),
    PART_2("part2"),
    TEST("test");

    private String value;

    DayPart(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

package ca.nickknack.day3;

public class PriorityUtil {
    public static int getPriority(Character character) {
        int asciiValue = character.charValue();

        if (AsciiUtil.isLowercaseLetter(asciiValue)) {
            return asciiValue - 96;
        }

        if (AsciiUtil.isUppercaseLetter(asciiValue)) {
            return asciiValue -38;
        }

        throw new IllegalArgumentException(String.format("%s is not a valid character for an item", character));
    }
}

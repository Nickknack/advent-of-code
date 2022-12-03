package ca.nickknack.day3;

public class AsciiUtil {

    public static boolean isLowercaseLetter(int value) {
        return value >= 97 && value < 123;
    }

    public static boolean isUppercaseLetter(int value) {
        return value >= 65 && value < 91;
    }
}

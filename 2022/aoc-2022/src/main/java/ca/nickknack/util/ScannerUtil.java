package ca.nickknack.util;

import java.io.InputStream;
import java.util.Scanner;

public class ScannerUtil {
    public static Scanner newScanner(String filename) {
        InputStream inputStream = ScannerUtil.class.getClassLoader().getResourceAsStream(filename);
        return new Scanner(inputStream);
    }
}

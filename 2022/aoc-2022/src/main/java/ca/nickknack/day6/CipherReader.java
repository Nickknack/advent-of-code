package ca.nickknack.day6;

import ca.nickknack.util.ScannerUtil;

import java.util.Scanner;

public class CipherReader {
    public Cipher readCipherFromFile(String filename) {
        Scanner scanner = ScannerUtil.newScanner(filename);

        Cipher cipher = new Cipher(scanner.nextLine());
        scanner.close();

        return cipher;
    }
}

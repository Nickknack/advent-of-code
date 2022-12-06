package ca.nickknack.day6;

public class Main {

    public static void main(String[] args) {
        int uniqueAmount = 4;
        Cipher cipher = new CipherReader().readCipherFromFile("day6-input");

        for (int i = 0; i < cipher.getMessageLength(); i++) {
            if (cipher.containsAllUniqueCharacters(i, uniqueAmount)) {
                System.out.println(String.format("first marker after character %s", i + uniqueAmount));
                break;
            }
        }
    }
}

package ca.nickknack.day6;

public class Main {

    public static void main(String[] args) {
        int uniqueAmountPacket = 4;
        int uniqueAmountMessage = 14;
        Cipher cipher = new CipherReader().readCipherFromFile("day6-input");

        for (int i = 0; i < cipher.getMessageLength(); i++) {
            if (cipher.containsAllUniqueCharacters(i, uniqueAmountPacket)) {
                System.out.println(String.format("first marker after character %s", i + uniqueAmountPacket));
                break;
            }
        }

        for (int i = 0; i < cipher.getMessageLength(); i++) {
            if (cipher.containsAllUniqueCharacters(i, uniqueAmountMessage)) {
                System.out.println(String.format("first message after character %s", i + uniqueAmountMessage));
                break;
            }
        }
    }
}

package ca.nickknack.day6;

import java.util.HashSet;
import java.util.Set;

public class Cipher {
    private String message;

    public Cipher(String message) {
        this.message = message;
    }

    public boolean containsAllUniqueCharacters(int startingIndex, int amount) {
        Set<Character> uniqueCharacters = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            if (uniqueCharacters.contains(message.charAt(startingIndex + i))) {
                break;
            }

            uniqueCharacters.add(message.charAt(startingIndex + i));
        }

        return uniqueCharacters.size() == amount;
    }

    public int getMessageLength() {
        return message.length();
    }
}

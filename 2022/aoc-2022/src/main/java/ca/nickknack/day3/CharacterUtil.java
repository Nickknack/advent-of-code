package ca.nickknack.day3;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterUtil {
    public static List<Character> stringToCharacters(String string) {
        return string.chars()
                .mapToObj(charInt -> Character.valueOf((char) charInt))
                .collect(Collectors.toList());
    }

    public static Set<Character> getMatchingItemSet(Set<Character> characters1, Set<Character> characters2) {
        return characters1.stream()
                .filter(item -> characters2.contains(item))
                .collect(Collectors.toSet());
    }
}

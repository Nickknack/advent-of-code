package ca.nickknack.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Compartment {
    private List<Character> items = new ArrayList<>();

    public Compartment(List<Character> items) {
        this.items = items;
    }

    public List<Character> getItems() {
        return this.items;
    }
}

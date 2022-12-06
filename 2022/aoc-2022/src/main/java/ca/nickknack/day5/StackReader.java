package ca.nickknack.day5;

import ca.nickknack.util.ScannerUtil;

import java.util.*;
import java.util.regex.Pattern;

public class StackReader {

    private static final Pattern STACKS_END_PATTERN = Pattern.compile("^[ 0-9]+");

    public Map<Integer, Deque<Character>> createStacks(String filename, boolean is9000) {
        Scanner scanner = ScannerUtil.newScanner(filename);
        int stackCount = getStackCount(filename);

        Map<Integer, Deque<Character>> stacks = new HashMap<>();
        for (int i = 0; i < stackCount; i++) {
            stacks.put(i, new ArrayDeque<>());
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches(STACKS_END_PATTERN.pattern())) {
                break;
            }

            addLineToStacks(line, stacks);
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isBlank()) {
                String[] moves = line.replace("move", "")
                        .replace("from", ",")
                        .replace("to", ",")
                        .replace(" ", "")
                        .split(",");

                int amountToMove = Integer.valueOf(moves[0]);
                int fromStack = Integer.valueOf(moves[1]) - 1;
                int toStack = Integer.valueOf(moves[2]) - 1;

                if (is9000) {
                    addToStack9000(amountToMove, stacks.get(toStack), stacks.get(fromStack));
                } else {
                    addToStack9001(amountToMove, stacks.get(toStack), stacks.get(fromStack));
                }
            }
        }

        scanner.close();

        return stacks;
    }

    private int getStackCount(String filename) {
        Scanner scanner = ScannerUtil.newScanner(filename);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches(STACKS_END_PATTERN.pattern())) {
                return line.replace(" ", "").length();
            }
        }
        throw new IllegalArgumentException("No stack description in file");
    }

    private void addLineToStacks(String line, Map<Integer, Deque<Character>> stacks) {
        for (int i = 0; i < line.length(); i+=4) {
            String crate = line.substring(i, i+3);
            if (!crate.isBlank()) {
                stacks.get(i/4).add(crate.charAt(1));
            }
        }
    }

    private void addToStack9000(int amountToMove, Deque<Character> toStack, Deque<Character> fromStack) {
        for (int i = 0; i < amountToMove; i++) {
            toStack.addFirst(fromStack.pop());
        }
    }

    private void addToStack9001(int amountToMove, Deque<Character> toStack, Deque<Character> fromStack) {
        Stack<Character> temp = new Stack<>();
        for (int i = 0; i < amountToMove; i++) {
            temp.push(fromStack.pop());
        }

        for (int i = 0; i < amountToMove; i++) {
            toStack.addFirst(temp.pop());
        }
    }
}

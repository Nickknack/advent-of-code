package ca.nickknack.day5;

import java.util.Deque;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        StackReader stackReader = new StackReader();
        String result = "";
        Map<Integer, Deque<Character>> stacks = stackReader.createStacks("day5-input", true);
        for (Deque<Character> stack : stacks.values()) {
            result += stack.peek().toString();
        }

        System.out.println(result);

        result = "";
        Map<Integer, Deque<Character>> stacks9001 = stackReader.createStacks("day5-input", false);
        for (Deque<Character> stack : stacks9001.values()) {
            result += stack.peek().toString();
        }

        System.out.println(result);
    }
}

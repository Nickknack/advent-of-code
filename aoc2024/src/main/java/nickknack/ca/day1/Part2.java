package nickknack.ca.day1;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Part2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list1 = new ArrayList<>();
        Map<Integer, Integer> secondListCount = new HashMap<>();
        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("test/day1/input1.txt"))) {
            while (scanner.hasNext()) {
                list1.add(scanner.nextInt());
                int list2Number = scanner.nextInt();
                secondListCount.put(list2Number, secondListCount.getOrDefault(list2Number, 0) + 1);
            }
        }

        int similarityScore = 0;
        for (int i = 0; i < list1.size(); i++) {
            similarityScore += list1.get(i) * secondListCount.getOrDefault(list1.get(i), 0);
        }

        System.out.println(similarityScore);
    }

}

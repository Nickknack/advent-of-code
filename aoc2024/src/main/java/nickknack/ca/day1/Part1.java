package nickknack.ca.day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Part1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("test/day1/input1.txt"))) {
            while (scanner.hasNext()) {
                list1.add(scanner.nextInt());
                list2.add(scanner.nextInt());
            }
        }

        CompletableFuture<List<Integer>> sortList1Future = CompletableFuture.supplyAsync(() -> sortList(list1));
        CompletableFuture<List<Integer>> sortList2Future = CompletableFuture.supplyAsync(() -> sortList(list2));

        CompletableFuture sortLists = CompletableFuture.allOf(sortList1Future, sortList2Future);

        sortLists.get();

        List<Integer> sortedList1 = sortList1Future.get();
        List<Integer> sortedList2 = sortList2Future.get();

        int totalDistance = 0;
        for (int i = 0; i < sortedList1.size(); i++) {
            totalDistance += Math.abs(sortedList1.get(i) - sortedList2.get(i));
        }

        System.out.println(totalDistance);
    }

    private static List<Integer> sortList(List<Integer> list) {
        List<Integer> listToSort = new ArrayList<>(list);
        Collections.sort(listToSort);

        return listToSort;
    }
}

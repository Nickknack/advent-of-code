package ca.nickknack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;

public class Day5 {
    public static void main(String[] args) {
        Almanac almanac = new Almanac();
        List<String> lines = new BufferedReader(new InputStreamReader(Day5.class.getClassLoader().getResourceAsStream("input.txt")))
                .lines()
                .toList();

        List<Long> seeds = getSeeds(lines.get(0));

        LookupMap currentMap = new LookupMap();
        for (int i = 3; i < lines.size(); i++) {
            if (lines.get(i).contains("map")) {
                almanac.addLookup(currentMap);
                currentMap = new LookupMap();
            } else {
                Optional<Range> range = parseRange(lines.get(i));
                if (range.isPresent()) {
                    currentMap.addRange(range.get());
                }
            }
        }

        almanac.addLookup(currentMap);

        long result = seeds.stream()
                .map(almanac::findLocationNumberForSeed)
                .min(Comparator.comparing(Function.identity()))
                .orElseThrow(() -> new RuntimeException("Ahhhh!!!"));

        System.out.println(result);
    }

    private static Optional<Range> parseRange(String line) {
        String[] values = line.stripLeading()
                .split(" ");
        if (values.length != 3) {
            return Optional.empty();
        }

        return Optional.of(new Range(Long.valueOf(values[1]), Long.valueOf(values[0]), Long.valueOf(values[2])));
    }

    private static List<Long> getSeeds(String line) {
        return Arrays.stream(line.replace("seeds:", "")
                .stripLeading()
                .split(" "))
                .map(Long::valueOf)
                .toList();
    }

    static class Almanac {
        private List<LookupMap> lookups = new ArrayList<>();

        public List<LookupMap> addLookup(LookupMap map) {
            this.lookups.add(map);
            return this.lookups;
        }

        public long findLocationNumberForSeed(long seed) {
            long currentVal = seed;
            for (LookupMap lookup: lookups) {
                currentVal = lookup.getResultVal(currentVal);
            }
            return currentVal;
        }
    }

    static class LookupMap {
        private List<Range> ranges = new ArrayList<>();

        public List<Range> addRange(Range range) {
            this.ranges.add(range);
            return ranges;
        }

        public long getResultVal(long val) {
            for (Range range: ranges) {
                Optional<Long> result = range.getNextValue(val);
                if (result.isPresent()) {
                    return result.get();
                }
            }
            return val;
        }
    }

    static class Range {
        private long sourceStart;
        private long destinationEnd;
        private long range;

        Range(long sourceStart, long destinationEnd, long range) {
            this.sourceStart = sourceStart;
            this.destinationEnd = destinationEnd;
            this.range = range;
        }

        public Optional<Long> getNextValue(long value) {
            if (value < sourceStart) {
                return Optional.empty();
            }

            if (value >= sourceStart + range) {
                return Optional.empty();
            }

            return Optional.of(value - sourceStart + destinationEnd);
        }
    }
}
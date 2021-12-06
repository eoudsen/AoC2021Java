package day6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class day6 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Long solve(final List<String> input, final int days) {
        var counts = Arrays.stream(input.get(0).split(",")).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        var numberCount = new HashMap<Integer, Long>();
        for (var countString : counts.keySet()) {
            numberCount.put(Integer.valueOf(countString), counts.get(countString));
        }
        numberCount.put(0, 0L);
        numberCount.put(6, 0L);
        numberCount.put(7, 0L);
        numberCount.put(8, 0L);
        for (var i = 0; i < days; i++) {
            var sixes = 0L;
            var eights = 0L;

            for (var j = 0; j < 9; j++) {
                if (numberCount.get(j) == 0) {
                    continue;
                }
                if (j == 0) {
                    sixes += numberCount.get(j);
                    eights += numberCount.get(j);
                } else {
                    numberCount.put(j - 1, numberCount.get(j));
                }
                numberCount.put(j, 0L);
            }
            numberCount.put(6, sixes + numberCount.get(6));
            numberCount.put(8, eights + numberCount.get(8));
        }
        return numberCount.values().stream().mapToLong(x -> Long.parseLong(String.valueOf(x))).sum();
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day6/input.txt");
        System.out.println(solve(fileContent, 80));
        System.out.println(solve(fileContent, 256));
    }
}

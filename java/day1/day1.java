package day1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day1 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer part1(final List<Integer> input) {
        var last = input.get(0);
        var counter = 0;
        for (var val : input.subList(1, input.size())) {
            if (val > last) {
                counter++;
            }
            last = val;
        }
        return counter;
    }

    public static Integer part2(final List<Integer> input) {
        var windows = new ArrayList<Integer>();
        for (var i = 0; i < input.size() - 2; i++) {
            windows.add(input.subList(i, i+3).stream().reduce(0, Integer::sum));
        }
        return part1(windows);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day1/input.txt");
        var intInput = fileContent.stream().map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(part1(intInput));
        System.out.println(part2(intInput));
    }
}
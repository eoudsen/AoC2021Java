package day7;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day7 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer solve(final List<Integer> input) {
        var middleValue = input.get(input.size() / 2);
        var result = 0;
        for (var crab : input) {
            result += Math.abs(crab - middleValue);
        }
        return result;
    }

    public static Integer solve2(final List<Integer> input) {
        var mean = input.stream().mapToDouble(a -> a).average().getAsDouble();
        var middleValue = mean % 1 < .75 ? Math.floor(mean) : Math.round(mean);
        var result = 0;
        for (var crab : input) {
            result += IntStream.range(0, (int) (Math.abs(crab - middleValue) + 1)).boxed().collect(Collectors.toList()).stream().reduce(0, Integer::sum);
        }
        return result;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day7/input.txt");
        var numbers = Arrays.stream(fileContent.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Collections.sort(numbers);
        System.out.println(solve(numbers));
        System.out.println(solve2(numbers));
    }
}

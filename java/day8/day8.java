package day8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class day8 {
    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer solve(final List<String> input) {
        var result = 0;
        for (var line : input) {
            var outputValues = line.split("\\|")[1].split(" ");
            for (var outputValue : outputValues) {
                if (outputValue.length() == 2
                        || outputValue.length() == 3
                        || outputValue.length() == 4
                        || outputValue.length() == 7) {
                    result++;
                }
            }
        }
        return result;
    }

    public static Integer solve2(final List<String> input) {
        var result = 0;
        for (var line : input) {
            var splitValues = line.split("\\|");
            var outputValues = splitValues[1].strip().split(" ");
            var inputValues = splitValues[0].split(" ");
            var segmentDisplay = new SegmentDisplay(inputValues);
            segmentDisplay.determineNumbers();
            segmentDisplay.calculateResultValue(outputValues);
            result += segmentDisplay.getResultValue();

        }
        return result;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day8/input.txt");
        System.out.println(solve(fileContent));
        System.out.println(solve2(fileContent));
    }
}

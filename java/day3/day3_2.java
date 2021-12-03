package day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day3_2 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer getMostCommon(final List<String> bitStrings, final Integer index) {
        var counter = 0;
        for (var bitString : bitStrings) {
            if (Integer.parseInt(bitString.substring(index, index + 1)) == 1) {
                counter++;
            }
        }
        return counter >= (double) bitStrings.size() /  (double) 2 ? 1 : 0;
    }

    public static Integer solve(final List<String> input) {
        var oxygenList = new ArrayList<>(input);
        for (var i = 0; i < input.get(0).length(); i++) {
            final var index = i;
            var mostCommon = getMostCommon(oxygenList, index);
            oxygenList = (ArrayList<String>) oxygenList.stream().filter(x -> Integer.valueOf(x.substring(index, index + 1)).equals(mostCommon)).collect(Collectors.toList());
            if (oxygenList.size() == 1) {
                break;
            }
        }

        var coList = new ArrayList<>(input);
        for (var i = 0; i < input.get(0).length(); i++) {
            final var index = i;
            var mostCommon = getMostCommon(coList, index) == 1 ? 0 : 1;
            coList = (ArrayList<String>) coList.stream().filter(x -> Integer.valueOf(x.substring(index, index + 1)).equals(mostCommon)).collect(Collectors.toList());
            if (coList.size() == 1) {
                break;
            }
        }

        var oxygen = Integer.parseInt(oxygenList.get(0), 2);
        var co = Integer.parseInt(coList.get(0), 2);
        return oxygen * co;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day3/input.txt");
        System.out.println(solve(fileContent));
    }
}

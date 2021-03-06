package day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day3 {

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
        return counter >= (double) bitStrings.size() / (double) 2 ? 1 : 0;
    }

    public static Integer solve(final List<String> input) {
        var gammaList = new ArrayList<Integer>();
        for (var i = 0; i < input.get(0).length(); i++) {
            gammaList.add(getMostCommon(input, i));
        }

        var epsilonList = new ArrayList<Integer>();
        for (var val : gammaList) {
            if (val == 1) {
                epsilonList.add(0);
            } else {
                epsilonList.add(1);
            }
        }

        var gamma = Integer.parseInt(gammaList.stream().map(String::valueOf).collect(Collectors.joining()) , 2);
        var epsilon = Integer.parseInt(epsilonList.stream().map(String::valueOf).collect(Collectors.joining()), 2);


        return gamma * epsilon;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day3/input.txt");
        System.out.println(solve(fileContent));
    }
}

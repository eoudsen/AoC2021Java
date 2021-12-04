package day4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day4_2 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static List<Board> generateBoards(final List<String> input) {
        var boards = new ArrayList<Board>();
        var lineCounter = 2;
        while (true) {
            if (lineCounter >= input.size()) {
                break;
            }
            boards.add(new Board(input.subList(lineCounter, lineCounter + 5)));
            lineCounter += 6;
        }
        return boards;
    }

    public static Integer solve(final List<String> input) {
        var numbersString = input.get(0).split(",");
        var numbers = Arrays.stream(numbersString).map(Integer::parseInt).collect(Collectors.toList());
        var boards = generateBoards(input);
        var boardMarkers = new int[boards.size()];
        Arrays.fill(boardMarkers, 1);
        for (var number : numbers) {
            for (var i = 0; i < boards.size(); i++) {
                if (boardMarkers[i] == 1 && boards.get(i).checkNumber(number) && boards.get(i).checkWon()) {
                    if (IntStream.of(boardMarkers).sum() == 1) {
                        return boards.get(i).getScore() * number;
                    }
                    boardMarkers[i] = 0;
                }
            }
        }

        return -1;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day4/input.txt");
        System.out.println(solve(fileContent));
    }
}
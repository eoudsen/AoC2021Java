package day4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class day4 {

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
        while (lineCounter < input.size()) {
            boards.add(new Board(input.subList(lineCounter, lineCounter + 5)));
            lineCounter += 6;
        }
        return boards;
    }

    public static Integer solve(final List<String> input) {
        var numbersString = input.get(0).split(",");
        var numbers = Arrays.stream(numbersString).map(Integer::parseInt).collect(Collectors.toList());
        var boards = generateBoards(input);

        for (var number : numbers) {
            for (var board : boards) {
                if (board.checkNumber(number) && board.checkWon()) {
                    return board.getScore() * number;
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
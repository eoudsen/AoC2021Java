package day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class day2 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static void executeCommand(final String command, SubMarine sub) {
        var split = command.split(" ");
        var cmd = split[0];
        var val = Integer.valueOf(split[1]);
        switch (cmd) {
            case "up":
                sub.setDepth(sub.getDepth() - val);
                break;
            case "down":
                sub.setDepth(sub.getDepth() + val);
                break;
            case "forward":
                sub.setHorizontal(sub.getHorizontal() + val);
                break;
        }
    }

    public static Integer solve(final List<String> input) {
        var sub = new SubMarine();
        for (var command : input) {
            executeCommand(command, sub);
        }
        return sub.getDepth() * sub.getHorizontal();
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day2/input.txt");
        System.out.println(solve(fileContent));
    }
}
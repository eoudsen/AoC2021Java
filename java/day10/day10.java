package day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class day10 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer runLine(final String line) {
        var stack = new Stack<Character>();
        for (var i = 0; i < line.strip().length(); i++) {
            var character = line.substring(i, i + 1);
            switch (character.charAt(0)) {
                case '<':
                case '{':
                case '[':
                case '(': stack.push(character.charAt(0)); break;
                case '>': {
                    if (stack.peek() != '<') {
                        return 25137;
                    }
                    stack.pop();
                    break;
                }
                case '}': {
                    if (stack.peek() != '{') {
                        return 1197;
                    }
                    stack.pop();
                    break;
                }
                case ']': {
                    if (stack.peek() != '[') {
                        return 57;
                    }
                    stack.pop();
                    break;
                }
                case ')': {
                    if (stack.peek() != '(') {
                        return 3;
                    }
                    stack.pop();
                    break;
                }
            }
        }
        return 0;
    }

    public static Integer solve(final List<String> input) {
        var result = 0;
        for (var line : input) {
            result += runLine(line.strip());
        }
        return result;
    }

    public static Long runLine2(final String line) {
        var stack = new Stack<Character>();
        for (var character : line.toCharArray()) {
            switch (character) {
                case '<':
                case '{':
                case '[':
                case '(':stack.push(character); break;
                case '>': {
                    if (stack.peek() != '<') {
                        return 0L;
                    }
                    stack.pop();
                    break;
                }
                case '}': {
                    if (stack.peek() != '{') {
                        return 0L;
                    }
                    stack.pop();
                    break;
                }
                case ']': {
                    if (stack.peek() != '[') {
                        return 0L;
                    }
                    stack.pop();
                    break;
                }
                case ')': {
                    if (stack.peek() != '(') {
                        return 0L;
                    }
                    stack.pop();
                    break;
                }
            }
        }

        var score = 0L;
        while (!stack.isEmpty()) {
            score *= 5;
            switch (stack.pop()) {
                case '<': score += 4; break;
                case '{': score += 3; break;
                case '[': score += 2; break;
                case '(': score += 1; break;
            }
        }
        return score;
    }

    public static Long solve2(final List<String> input) {
        var scoreList = new ArrayList<Long>();
        for (var line : input) {
            var returnValue = runLine2(line);
            if (returnValue != 0) {
                scoreList.add(returnValue);
            }
        }
        Collections.sort(scoreList);
        return scoreList.get(scoreList.size() / 2);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day10/input.txt");
        System.out.println(solve(fileContent));
        System.out.println(solve2(fileContent));
    }
}

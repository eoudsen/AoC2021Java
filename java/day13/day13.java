package day13;

import day12.CaveSystem;
import day9.Coordinate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class day13 {
    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static void fold(final Fold fold, Set<Coordinate> points) {
        if (fold.getDirection().equals("x")) {
            foldX(fold, points);
        } else {
            foldY(fold, points);
        }
    }

    public static void foldX(final Fold fold, Set<Coordinate> points) {
        var iterateSet = new HashSet<>(points);
        for (var point : iterateSet) {
            if (point.getX() > fold.getValue()) {
                var newPoint = new Coordinate(point.getX() - ((point.getX() - fold.getValue()) * 2), point.getY());
                points.remove(point);
                points.add(newPoint);
            }
        }
    }

    public static void foldY(final Fold fold, Set<Coordinate> points) {
        var iterateSet = new HashSet<>(points);
        for (var point : iterateSet) {
            if (point.getY() > fold.getValue()) {
                var newPoint = new Coordinate(point.getX(), point.getY() - ((point.getY() - fold.getValue()) * 2));
                points.remove(point);
                points.add(newPoint);
            }
        }
    }

    public static Integer solve(final List<String> input) {
        var points = new HashSet<Coordinate>();
        var counter = 0;
        while (input.get(counter).length() > 1) {
            var splitValues = input.get(counter).strip().split(",");
            var x = splitValues[0];
            var y = splitValues[1];
            points.add(new Coordinate(Integer.valueOf(x), Integer.valueOf(y)));
            counter++;
        }
        counter++;

        var folds = new ArrayList<Fold>();
        while (input.get(counter).length() > 1 && counter < input.size() - 1) {
            var splitValues = input.get(counter).strip().split("=");
            var line = Integer.valueOf(splitValues[1]);
            var axis = splitValues[0].split(" ")[2];
            folds.add(new Fold(axis, line));
            counter++;
        }

        fold(folds.get(0), points);
        return points.size();
    }

    public static void solve2(final List<String> input) {
        var points = new HashSet<Coordinate>();
        var counter = 0;
        while (input.get(counter).length() > 1) {
            var splitValues = input.get(counter).strip().split(",");
            var x = splitValues[0];
            var y = splitValues[1];
            points.add(new Coordinate(Integer.valueOf(x), Integer.valueOf(y)));
            counter++;
        }
        counter++;

        var folds = new ArrayList<Fold>();
        while (counter < input.size() && input.get(counter).length() > 1) {
            var splitValues = input.get(counter).strip().split("=");
            var line = Integer.valueOf(splitValues[1]);
            var axis = splitValues[0].split(" ")[2];
            folds.add(new Fold(axis, line));
            counter++;
        }

        for (var fold : folds) {
            fold(fold, points);
        }

        var maxX = 0;
        var maxY = 0;
        for (var coord : points) {
            if (coord.getX() > maxX) {
                maxX = coord.getX();
            }
            if (coord.getY() > maxY) {
                maxY = coord.getY();
            }
        }

        for (var y = 0; y < maxY + 1; y++) {
            var stringBuilder = new StringBuilder();
            for (var x = 0; x < maxX + 1; x++) {
                if (points.contains(new Coordinate(x, y))) {
                    stringBuilder.append("#");
                } else {
                    stringBuilder.append(".");
                }
            }
            System.out.println(stringBuilder);
        }
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day13/input.txt");
        System.out.println(solve(fileContent));
        solve2(fileContent);
    }
}

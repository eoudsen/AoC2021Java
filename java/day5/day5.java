package day5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day5 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static void updatePoints(Map<Integer, Point> pointMap, List<Point> updatePoints) {
        for (var point : updatePoints) {
            if (pointMap.containsKey(point.hashCode())) {
                pointMap.get(point.hashCode()).update();
            } else {
                point.update();
                pointMap.put(point.hashCode(), point);
            }
        }
    }

    public static Integer solve(final List<String> input, final int part) {
        var pattern = Pattern.compile("(?<x1>\\d+),(?<y1>\\d+)\\s*->\\s*(?<x2>\\d+),(?<y2>\\d+)");
        var pointMap = new HashMap<Integer, Point>();
        for (var line : input) {
            var matcher = pattern.matcher(line);
            Integer x1, y1, x2, y2;
            if (matcher.find()) {
                x1 = Integer.valueOf(matcher.group(1));
                y1 = Integer.valueOf(matcher.group(2));
                x2 = Integer.valueOf(matcher.group(3));
                y2 = Integer.valueOf(matcher.group(4));
            } else {
                return -1;
            }
            if (x1.equals(x2)) {
                var range = IntStream.range(y1, y2 + 1).boxed().collect(Collectors.toList());
                if (y1 > y2) {
                    range = IntStream.range(y2, y1 + 1).boxed().collect(Collectors.toList());
                }
                var pointList = new ArrayList<Point>();
                for (var i : range) {
                    pointList.add(new Point(x1, i));
                }
                updatePoints(pointMap, pointList);
            } else if (y1.equals(y2)) {
                var range = IntStream.range(x1, x2 + 1).boxed().collect(Collectors.toList());
                if (x1 > x2) {
                    range = IntStream.range(x2, x1 + 1).boxed().collect(Collectors.toList());
                }
                var pointList = new ArrayList<Point>();
                for (var i : range) {
                    pointList.add(new Point(i, y1));
                }
                updatePoints(pointMap, pointList);
            } else if (part == 2) {
                var xrange = IntStream.range(x1, x2 + 1).boxed().collect(Collectors.toList());
                var yrange = IntStream.range(y1, y2 + 1).boxed().collect(Collectors.toList());
                if (y1 > y2) {
                    yrange = IntStream.range(y2, y1 + 1).boxed().collect(Collectors.toList());
                    Collections.reverse(yrange);
                }
                if (x1 > x2) {
                    xrange = IntStream.range(x2, x1 + 1).boxed().collect(Collectors.toList());
                    Collections.reverse(xrange);
                }
                var pointList = new ArrayList<Point>();
                for (var i = 0; i < xrange.size(); i++) {
                    pointList.add(new Point(xrange.get(i), yrange.get(i)));
                }
                updatePoints(pointMap, pointList);
            }
        }

        var finalValue = 0;
        for (var point : pointMap.values()) {
            if (point.getLines() > 1) {
                finalValue += 1;
            }
        }
        return finalValue;
    }


    public static void main(String... args) {
        var fileContent = getFileContent("java/day5/input.txt");
        System.out.println(solve(fileContent, 1));
        System.out.println(solve(fileContent, 2));
    }
}

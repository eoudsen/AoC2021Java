package day11;

import day9.Coordinate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class day11 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static List<Coordinate> retrieveNeighbours(final Integer x, final Integer y, final Integer[][] grid) {
        var neighbors = new ArrayList<Coordinate>();
        neighbors.add(new Coordinate(-1, 0));
        neighbors.add(new Coordinate(0, 1));
        neighbors.add(new Coordinate(1, 0));
        neighbors.add(new Coordinate(0, -1));
        neighbors.add(new Coordinate(-1, -1));
        neighbors.add(new Coordinate(1, 1));
        neighbors.add(new Coordinate(-1, 1));
        neighbors.add(new Coordinate(1, -1));
        var values = new ArrayList<Coordinate>();
        for (var coordinate : neighbors) {
            var nx = x + coordinate.getX();
            var ny = y + coordinate.getY();
            if (0 <= ny && ny < grid[0].length && 0 <= nx && nx < grid.length) {
                values.add(new Coordinate(nx, ny));
            }
        }
        return values;
    }

    public static void updateGrid(Integer[][] grid) {
        for (var x = 0; x < grid.length; x++) {
            for (var y = 0; y < grid[x].length; y++) {
                grid[x][y]++;
            }
        }
    }

    public static Integer solve(final Integer[][] grid) {
        var result = 0;
        for (var i = 0; i < 100; i++) {
            var flashed = new HashSet<Coordinate>();
            updateGrid(grid);
            var changed = true;
            while (changed) {
                changed = false;
                for (var x = 0; x < grid.length; x++) {
                    for (var y = 0; y < grid[x].length; y++) {
                        if (grid[x][y] > 9 && !flashed.contains(new Coordinate(x, y))) {
                            changed = true;
                            flashed.add(new Coordinate(x, y));
                            grid[x][y] = 0;
                            for (var coord : retrieveNeighbours(x, y, grid)) {
                                if (!flashed.contains(coord)) {
                                    grid[coord.getX()][coord.getY()] += 1;
                                }
                            }
                        }
                    }
                }
            }
            result += flashed.size();
        }
        return result;
    }

    public static Integer solve2(final Integer[][] grid) {
        var counter = 0;
        while (true) {
            var flashed = new HashSet<Coordinate>();
            updateGrid(grid);
            var changed = true;
            while (changed) {
                changed = false;
                for (var x = 0; x < grid.length; x++) {
                    for (var y = 0; y < grid[x].length; y++) {
                        if (grid[x][y] > 9 && !flashed.contains(new Coordinate(x, y))) {
                            changed = true;
                            flashed.add(new Coordinate(x, y));
                            grid[x][y] = 0;
                            for (var coord : retrieveNeighbours(x, y, grid)) {
                                if (!flashed.contains(coord)) {
                                    grid[coord.getX()][coord.getY()] += 1;
                                }
                            }
                        }
                    }
                }
            }
            counter += 1;
            if (flashed.size() == 100) {
                return counter;
            }
        }
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day11/input.txt");
        var grid = new Integer[fileContent.size()][fileContent.get(0).length()];
        for (var i = 0; i < fileContent.size(); i++) {
            for (var j = 0; j < fileContent.get(i).length(); j++) {
                grid[i][j] = Integer.valueOf(fileContent.get(i).substring(j, j + 1));
            }
        }
        System.out.println(solve(grid));
        grid = new Integer[fileContent.size()][fileContent.get(0).length()];
        for (var i = 0; i < fileContent.size(); i++) {
            for (var j = 0; j < fileContent.get(i).length(); j++) {
                grid[i][j] = Integer.valueOf(fileContent.get(i).substring(j, j + 1));
            }
        }
        System.out.println(solve2(grid));
    }
}

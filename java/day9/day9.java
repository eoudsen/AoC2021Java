package day9;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class day9 {

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

    public static Integer solve(final Integer[][] grid) {
        var risk = 0;
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                var currVal = grid[i][j];
                if (i < grid.length - 1 && grid[i+1][j] <= currVal) {
                    continue;
                }
                if (i > 0 && grid[i-1][j] <= currVal) {
                    continue;
                }
                if (j > 0 && grid[i][j-1] <= currVal) {
                    continue;
                }
                if (j < grid[i].length - 1 && grid[i][j+1] <= currVal) {
                    continue;
                }
                risk += currVal + 1;
            }
        }
        return risk;
    }

    public static Set<Coordinate> breadthFirstSearch(final Integer x, final Integer y, final Integer[][] grid, Set<Coordinate> visited) {
        visited.add(new Coordinate(x, y));
        var neighbours = new HashSet<Coordinate>();
        neighbours.addAll(retrieveNeighbours(x, y, grid));
        neighbours.removeAll(visited);
        for (var coordinate : neighbours) {
            if (grid[coordinate.getX()][coordinate.getY()] == 9) {
                continue;
            }
            visited.add(new Coordinate(coordinate.getX(), coordinate.getY()));
            visited.addAll(breadthFirstSearch(coordinate.getX(), coordinate.getY(), grid, visited));
        }
        return visited;
    }

    public static Integer solve2(final Integer[][] grid) {
        var visited = new HashSet<Coordinate>();
        var basins = new ArrayList<Integer>();
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 9 || visited.contains(new Coordinate(i, j))) {
                    continue;
                }
                var basin = breadthFirstSearch(i, j, grid, new HashSet<Coordinate>());
                visited.addAll(basin);
                basins.add(basin.size());
            }
        }
        Collections.sort(basins);
        var subList = basins.subList(basins.size() - 3, basins.size());
        return subList.stream().reduce(1, (a, b) -> a * b);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day9/input.txt");
        var grid = new Integer[fileContent.size()][fileContent.get(0).length()];
        for (var i = 0; i < fileContent.size(); i++) {
            for (var j = 0; j < fileContent.get(i).length(); j++) {
                grid[i][j] = Integer.valueOf(fileContent.get(i).substring(j, j + 1));
            }
        }
        System.out.println(solve(grid));
        System.out.println(solve2(grid));
    }
}

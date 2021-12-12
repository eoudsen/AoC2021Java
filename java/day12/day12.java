package day12;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class day12 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static void pathSearch(final CaveSystem caveSystem, final String currCave, final List<String> path, Set<List<String>> paths, Boolean duplicateSmall) {
        path.add(currCave);
        if (currCave.equals("end")) {
            paths.add(path);
            return;
        }
        for (var cave : caveSystem.getNeighbours(currCave)) {
            if (!path.contains(cave) && !cave.equals("start")) {
                var newList = new ArrayList<>(path);
                pathSearch(caveSystem, cave, newList, paths, duplicateSmall);
            } else if (!cave.equals(cave.toLowerCase())) {
                var newList = new ArrayList<>(path);
                pathSearch(caveSystem, cave, newList, paths, duplicateSmall);
            } else if (!duplicateSmall && !cave.equals("start")) {
                var newList = new ArrayList<>(path);
                pathSearch(caveSystem, cave, newList, paths, true);
            }
        }
    }

    public static Integer solve(final CaveSystem caveSystem) {
        var resultSet = new HashSet<List<String>>();
        pathSearch(caveSystem, "start", new ArrayList<>(), resultSet, true);
        return resultSet.size();
    }

    public static Integer solve2(final CaveSystem caveSystem) {
        var resultSet = new HashSet<List<String>>();
        pathSearch(caveSystem, "start", new ArrayList<>(), resultSet, false);
        return resultSet.size();
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/day12/input.txt");
        var caveSystem = new CaveSystem();
        for (var line : fileContent) {
            var cave1 = line.strip().split("-")[0];
            var cave2 = line.strip().split("-")[1];
            if (!caveSystem.getCaves().contains(cave1)) {
                caveSystem.addCave(cave1);
            }
            if (!caveSystem.getCaves().contains(cave2)) {
                caveSystem.addCave(cave2);
            }
            caveSystem.addNeighbour(cave1, cave2);
            caveSystem.addNeighbour(cave2, cave1);
        }
        System.out.println(solve(caveSystem));
        System.out.println(solve2(caveSystem));
    }
}

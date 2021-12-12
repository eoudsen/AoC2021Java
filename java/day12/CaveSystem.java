package day12;

import java.util.*;

public class CaveSystem {

    private final Map<String, Set<String>> caveMapping;

    public CaveSystem() {
        this.caveMapping = new HashMap<String, Set<String>>();
    }

    public Set<String> getCaves() {
        return this.caveMapping.keySet();
    }

    public void addCave(final String caveName) {
        this.caveMapping.put(caveName, new HashSet<>());
    }

    public void addNeighbour(final String caveName, final String neighbourName) {
        var currNeighbours = this.caveMapping.get(caveName);
        currNeighbours.add(neighbourName);
        this.caveMapping.put(caveName, currNeighbours);
    }

    public Set<String> getNeighbours(final String caveName) {
        return this.caveMapping.get(caveName);
    }
}

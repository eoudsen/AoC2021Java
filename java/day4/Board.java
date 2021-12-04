package day4;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Board {

    private Integer[][] grid;
    private int[][] markers;

    public Board(final List<String> input) {
        var size = input.get(0).strip().split("\\s+").length;
        this.grid = new Integer[size][size];
        this.markers = new int[size][size];
        for (var i = 0; i < input.size(); i++) {
            var stringArray = input.get(i).strip().split("\\s+");
            var intArray = new Integer[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                intArray[j] = Integer.valueOf(stringArray[j]);
                this.markers[i][j] = 0;
            }
            this.grid[i] = intArray;
        }
    }

    public boolean checkNumber(final Integer number) {
        var found = false;
        for (var i = 0; i < this.grid.length; i++) {
            for (var j = 0; j < this.grid.length; j++) {
                if (Objects.equals(this.grid[i][j], number)) {
                    found = true;
                    this.markers[i][j] = 1;
                    break;
                }
            }
        }
        return found;
    }

    public boolean checkWon() {
        for (var i = 0; i < this.grid.length; i++) {
            if (IntStream.of(this.markers[i]).sum() == 5) {
                return true;
            }
            var sum = 0;
            for (var j = 0; j < this.grid.length; j++) {
                sum += this.markers[j][i];
            }
            if (sum == 5) {
                return true;
            }
        }
        return false;
    }

    public Integer getScore() {
        var value = 0;
        for (var i = 0; i < this.grid.length; i++) {
            for (var j = 0; j < this.grid.length; j++) {
                if (this.markers[i][j] == 0) {
                    value += this.grid[i][j];
                }
            }
        }
        return value;
    }
}

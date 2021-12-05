package day5;

import java.util.Objects;

public class Point {

    private final Integer x;
    private final Integer y;
    private Integer lines = 0;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getLines() {
        return this.lines;
    }

    public void update() {
        this.lines += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(x, point.x) && Objects.equals(y, point.y);
    }

    @Override
    public int hashCode() {
        return (((this.x + this.y) * (this.x + this.y + 1)) / 2) + this.x;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", len=" + lines +
                '}';
    }
}
